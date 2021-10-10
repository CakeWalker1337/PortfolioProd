package com.retroblade.hirasawaprod.content.ui.adapter

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.utils.loadCompressedImage

/**
 * The adapter class for horizontal photo container
 */
class ContentHorizontalAdapter(container: LinearLayout) : BaseContentAdapter(container) {

    override fun updateView(clickListener: ((String) -> Unit)?) {
        containerRef?.get()?.let { container ->
            // remove all existing items and fill the container with new items (full redraw)
            container.removeAllViews()
            val inflater = LayoutInflater.from(container.context)

            for (index in 0 until items.size) {
                val item = items[index]
                val frame = inflater.inflate(R.layout.item_content_feed_horizontal, container, false)
                val imageView = frame.findViewById<ImageView>(R.id.feedImage)
                container.addView(frame)

                imageView.loadCompressedImage(item.url)
                imageView.setOnClickListener {
                    clickListener?.invoke(items.getOrNull(index)?.id ?: "")
                }
            }
        }
    }
}