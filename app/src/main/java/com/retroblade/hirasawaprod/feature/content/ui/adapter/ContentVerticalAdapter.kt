package com.retroblade.hirasawaprod.feature.content.ui.adapter

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.utils.loadCompressedImage


/**
 * The adapter class for vertical photo container
 */
class ContentVerticalAdapter(container: LinearLayout) : BaseContentAdapter(container) {

    override fun updateView(clickListener: ((photoId: String) -> Unit)?) {
        containerRef?.get()?.let { container ->
            // remove all existing items and fill the container with new items (full redraw)
            container.removeAllViews()
            val inflater = LayoutInflater.from(container.context)

            for (index in 0 until items.size) {
                val item = items[index]
                val frame = inflater.inflate(R.layout.item_content_feed_vertical, container, false)
                val imageView = frame.findViewById<ImageView>(R.id.feedImage)
                val likesInfoView = frame.findViewById<TextView>(R.id.likesInfoView)
                val viewsInfoView = frame.findViewById<TextView>(R.id.viewsInfoView)

                container.addView(frame)

                likesInfoView.text = item.likes.toString()
                viewsInfoView.text = item.views.toString()

                imageView.loadCompressedImage(item.url)
                imageView.setOnClickListener {
                    clickListener?.invoke(items.getOrNull(index)?.id ?: "")
                }
            }
        }
    }
}