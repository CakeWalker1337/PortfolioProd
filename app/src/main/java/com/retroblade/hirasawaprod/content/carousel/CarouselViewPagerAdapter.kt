package com.retroblade.hirasawaprod.content

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.retroblade.hirasawaprod.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_content_carousel.view.*

/**
 * @author m.a.kovalev
 */
class CarouselViewPagerAdapter : RecyclerView.Adapter<CarouselViewPagerAdapter.SyntViewHolder>() {

    private val items: MutableList<PictureItem> = mutableListOf()

    fun setItems(newItems: List<PictureItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntViewHolder =
        SyntViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_carousel, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SyntViewHolder, position: Int) {
        with(holder) {
            Glide.with(holder.containerView)
                .load(items[position].url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(CenterCrop())
                .into(holder.containerView.contentImage)
        }
    }

    class SyntViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}
