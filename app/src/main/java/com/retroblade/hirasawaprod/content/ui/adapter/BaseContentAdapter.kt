package com.retroblade.hirasawaprod.content.ui.adapter

import android.widget.LinearLayout
import com.retroblade.hirasawaprod.content.ui.entity.PhotoItem
import java.lang.ref.WeakReference

/**
 * @author m.a.kovalev
 */
abstract class BaseContentAdapter(container: LinearLayout) {

    init {
        setContainer(container)
    }

    protected var containerRef: WeakReference<LinearLayout?>? = null
    protected val items: MutableList<PhotoItem> = mutableListOf()
    private var clickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(clickListener: (String) -> Unit) {
        this.clickListener = clickListener
    }

    fun setItems(newItems: List<PhotoItem>) {
        items.clear()
        items.addAll(newItems)
        updateView(clickListener)
    }

    fun setContainer(newContainer: LinearLayout?) {
        containerRef = WeakReference<LinearLayout?>(newContainer)
    }

    protected abstract fun updateView(clickListener: ((photoId: String) -> Unit)? = null)
}