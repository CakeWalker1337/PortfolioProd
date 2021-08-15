package com.retroblade.hirasawaprod.content

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.retroblade.hirasawaprod.R

/**
 * @author m.a.kovalev
 */
class ContentVerticalAdapter(container: LinearLayout) : BaseContentAdapter(container) {

    override fun updateView() {
        containerRef?.get()?.let { container ->
            container.removeAllViews()
            val inflater = LayoutInflater.from(container.context)
            for (item in items) {
                val frame = inflater.inflate(R.layout.item_content_feed_vertical, container, false)
                val imageView = frame.findViewById<ImageView>(R.id.feedImage)
                val likesInfoView = frame.findViewById<TextView>(R.id.likesInfoView)
                val viewsInfoView = frame.findViewById<TextView>(R.id.viewsInfoView)
                container.addView(frame)
                Glide.with(imageView)
                    .load(item.url)
                    .into(imageView)

                likesInfoView.text = item.likes.toString()
                viewsInfoView.text = item.views.toString()
            }
        }
    }
}