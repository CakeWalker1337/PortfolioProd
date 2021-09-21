package com.retroblade.hirasawaprod.content.ui.adapter

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.retroblade.hirasawaprod.R

/**
 * @author m.a.kovalev
 */
class ContentHorizontalAdapter(container: LinearLayout) : BaseContentAdapter(container) {

    override fun updateView(clickListener: ((String) -> Unit)?) {
        containerRef?.get()?.let { container ->
            container.removeAllViews()
            val inflater = LayoutInflater.from(container.context)
            for (index in 0 until items.size) {
                val item = items[index]
                val frame = inflater.inflate(R.layout.item_content_feed_horizontal, container, false)
                val imageView = frame.findViewById<ImageView>(R.id.feedImage)
                container.addView(frame)

                Glide.with(imageView)
                    .load(item.url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)

                imageView.setOnClickListener {
                    clickListener?.invoke(items.getOrNull(index)?.id ?: "")
                }
            }
        }
    }
}