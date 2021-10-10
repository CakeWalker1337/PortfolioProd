package com.retroblade.hirasawaprod.content.ui.adapter

import android.widget.LinearLayout
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import java.lang.ref.WeakReference

/**
 * Base adapter class used for displaying photo items
 */
abstract class BaseContentAdapter(container: LinearLayout) {

    // weak ref to container view. Must be weak for the case when the view needs to be destroyed
    protected var containerRef: WeakReference<LinearLayout?>? = null
    protected val items: MutableList<PhotoItem> = mutableListOf()
    private var clickListener: ((String) -> Unit)? = null

    init {
        setContainer(container)
    }

    /**
     * Sets photo [clickListener] to the adapter
     */
    fun setOnItemClickListener(clickListener: (String) -> Unit) {
        this.clickListener = clickListener
    }

    /**
     * Sets the list of [newItems] to the adapter and updates view
     */
    fun setItems(newItems: List<PhotoItem>) {
        items.clear()
        items.addAll(newItems)
        updateView(clickListener)
    }

    /**
     * Sets [container] weak reference to the adapter
     */
    fun setContainer(container: LinearLayout?) {
        containerRef = WeakReference<LinearLayout?>(container)
    }

    /**
     * Updates view with current set of items and passed [clickListener]
     */
    protected abstract fun updateView(clickListener: ((photoId: String) -> Unit)? = null)
}