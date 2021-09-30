package com.retroblade.hirasawaprod.common.ui

import android.view.View

/**
 * @author m.a.kovalev
 */
class CompositeAnimationManager private constructor(
    private val fadeInTime: Long,
    private val fadeOutTime: Long,
    private val items: List<AnimationItemNode>
) {

    fun startAnimation() {
        items.forEach { node ->
            animateRecursive(node)
        }
    }

    private fun animateRecursive(node: AnimationItemNode, lastEndTiming: Long = 0L) {
        val item = node.item
        item.view.get()?.let { view ->
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

    private fun View?.animateBlink(startDelay: Long, interval: Long, callBack: (() -> Unit)? = null) {
        this?.animate()?.setStartDelay(startDelay)?.alpha(1.0F)?.setDuration(fadeInTime)
            ?.withEndAction {
                this?.animate()?.setStartDelay(interval - fadeInTime - fadeOutTime)?.alpha(0.0F)?.setDuration(fadeOutTime)
                    ?.withEndAction {
                        callBack?.invoke()
                    }?.start()
            }?.start()
    }

    class Builder {

        private var fadeInTime: Long = DEFAULT_FADE_IN_TIME
        private var fadeOutTime: Long = DEFAULT_FADE_OUT_TIME
        private val items: MutableMap<Int, MutableList<AnimationItemNode>> = mutableMapOf()
        private val endNodes: MutableMap<Int, AnimationItemNode> = mutableMapOf()

        fun withFadeInAndOutTime(fadeInTime: Long, fadeOutTime: Long) {
            if (fadeInTime < 0L || fadeOutTime < 0L) {
                throw IllegalStateException("Fade in/out time must be positive or null")
            }
            this.fadeInTime = fadeInTime
            this.fadeOutTime = fadeOutTime
        }

        fun addItem(item: CompositeAnimationItem) {
            when {
                item.from < 0L ->
                    throw IllegalStateException("Item `from` value must be positive")
                item.to - item.from < 0L ->
                    throw IllegalStateException("Item animation interval must be positive")
                item.to - item.from > fadeInTime + fadeOutTime ->
                    throw IllegalStateException("Item animation interval must be longer, than current fade in + fade out animation")
            }
            item.view.get()?.let { view ->
                val key = view.hashCode()
                val itemsForView = items.getOrPut(key, { mutableListOf() })
                if (itemsForView.find { it.item.from < item.to && it.item.to > item.from } == null) {
                    val itemNode = AnimationItemNode(item = item)
                    val endNode = endNodes[key]
                    if (endNode == null) {
                        endNodes.put(key, itemNode)
                        itemsForView.add(itemNode)
                    } else {
                        endNode.next = itemNode
                    }
                } else {
                    throw IllegalStateException("Item interval overlaps with some of other items for this view")
                }
            }
        }

        fun build(): CompositeAnimationManager {
            return CompositeAnimationManager(fadeInTime, fadeOutTime, items.values.mapNotNull { it.firstOrNull() })
        }

        private companion object {
            private const val DEFAULT_FADE_IN_TIME = 500L
            private const val DEFAULT_FADE_OUT_TIME = 500L
        }
    }

    private class AnimationItemNode(
        var next: AnimationItemNode? = null,
        var item: CompositeAnimationItem,
    )
}