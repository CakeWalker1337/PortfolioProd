package com.retroblade.portfolioprod.common.ui

import android.view.View

/**
 * An animation helper class which performs fade in / fade out animations for specific views in bounds of a timeline
 *
 * Example (I - fade in timing, O - fade out timing):
 * Timeline: ------------------------
 *          0s                      10s
 * View1:    ----I-------O--I-------O
 *              2s       5s 6s      10s
 * View2:    ----I----------O--------
 *              2s          6s
 *
 * [items]: list [Node1(v1, 2s, 5s) -> Node2(v1, 6s, 10s), Node3(v2, 2s, 6s)].
 * Each next node calls the animation recursively when previous animation is going to end for this view
 * Nodes can't cross timings with nodes, that belong the same view. Also node timing interval can't be less than
 * the duration of [fadeInTime] + [fadeOutTime] animation
 */
class CompositeAnimationManager private constructor(
    private val fadeInTime: Long,
    private val fadeOutTime: Long,
    private val items: List<AnimationItemNode>
) {

    /**
     * Starts the animation for current set of nodes
     */
    fun startAnimation() {
        items.forEach { node ->
            animateRecursive(node)
        }
    }

    /**
     * Launches the animation for current [node] since [lastEndTiming] (a little optimization for reducing start delay)
     * [lastEndTiming] is an end timing of previous animation for this view
     */
    private fun animateRecursive(node: AnimationItemNode, lastEndTiming: Long = 0L) {
        val item = node.item
        item.view.get()?.let { view ->
            view.text = item.text
            view.animateBlink(
                startDelay = item.from - lastEndTiming,
                interval = item.to - item.from,
                callBack = {
                    item.endAction?.invoke()
                    if (node.next != null) {
                        animateRecursive(node.next!!, item.to)
                    }
                }
            )
        }
    }

    /**
     * Preforms blink animation for [this] view. Uses fadein and fadeout timings, that were set in animation manager
     * @param startDelay animation start delay
     * @param interval time that view "stays" shown (including fadein and fadeout timings)
     * @param callBack action that will be performed after the view is gone
     */
    private fun View?.animateBlink(startDelay: Long, interval: Long, callBack: (() -> Unit)? = null) {
        this?.animate()?.setStartDelay(startDelay)?.alpha(1.0F)?.setDuration(fadeInTime)
            ?.withEndAction {
                this?.animate()?.setStartDelay(interval - fadeInTime - fadeOutTime)?.alpha(0.0F)?.setDuration(fadeOutTime)
                    ?.withEndAction {
                        callBack?.invoke()
                    }?.start()
            }?.start()
    }

    /**
     * A builder class that constructs the animation manager object
     */
    class Builder {

        private var fadeInTime: Long = DEFAULT_FADE_IN_TIME
        private var fadeOutTime: Long = DEFAULT_FADE_OUT_TIME

        // items contain list of all nodes because it needs to check if timings of new node are not crossing for the same view
        private val items: MutableMap<Int, MutableList<AnimationItemNode>> = mutableMapOf()

        // end nodes map for adding to end optimization
        private val endNodes: MutableMap<Int, AnimationItemNode> = mutableMapOf()

        /**
         * Sets [fadeInTime] and [fadeOutTime] settings for the animation managers
         */
        fun withFadeInAndOutTime(fadeInTime: Long, fadeOutTime: Long): Builder {
            if (fadeInTime < 0L || fadeOutTime < 0L) {
                throw IllegalStateException("Fade in/out time must be positive or null")
            }
            this.fadeInTime = fadeInTime
            this.fadeOutTime = fadeOutTime
            return this
        }

        /**
         * Adds [item] node in items set
         */
        fun addItem(item: CompositeAnimationItem): Builder {
            when {
                item.from < 0L ->
                    throw IllegalStateException("Item `from` value must be positive")
                item.to - item.from < 0L ->
                    throw IllegalStateException("Item animation interval must be positive")
                item.to - item.from < fadeInTime + fadeOutTime ->
                    throw IllegalStateException("Item animation interval must be longer, than current fade in + fade out animation")
            }
            item.view.get()?.let { view ->
                // no need to use view as a key, because we would hold the useless reference otherwise
                val key = view.hashCode()
                val itemsForView = items.getOrPut(key, { mutableListOf() })
                // check timings overlapping
                if (itemsForView.find { it.item.from < item.to && it.item.to > item.from } == null) {
                    val itemNode = AnimationItemNode(item = item)
                    itemsForView.add(itemNode)
                    val endNode = endNodes[key]
                    if (endNode == null) {
                        // if there are no items for this view yet, make node the first
                        endNodes.put(key, itemNode)
                    } else {
                        // if there is already at least one node for view, hold the reference
                        // to current node as "next" and set new node as last
                        endNode.next = itemNode
                        endNodes[key] = itemNode
                    }
                } else {
                    throw IllegalStateException("Item interval overlaps with some of other items for this view")
                }
            }
            return this
        }

        /**
         * Builds animation manager object with declared params
         */
        fun build(): CompositeAnimationManager {
            return CompositeAnimationManager(fadeInTime, fadeOutTime, items.values.mapNotNull { it.firstOrNull() })
        }

        private companion object {
            private const val DEFAULT_FADE_IN_TIME = 500L
            private const val DEFAULT_FADE_OUT_TIME = 500L
        }
    }

    /**
     * Node class exposes a structure of animation timeline
     */
    private class AnimationItemNode(
        var next: AnimationItemNode? = null,
        var item: CompositeAnimationItem,
    )
}