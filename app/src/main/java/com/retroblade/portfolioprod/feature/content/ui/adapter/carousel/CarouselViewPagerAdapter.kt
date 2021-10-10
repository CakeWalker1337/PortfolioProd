package com.retroblade.portfolioprod.feature.content.ui.adapter.carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.retroblade.portfolioprod.R
import com.retroblade.portfolioprod.feature.content.ui.model.PhotoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_content_carousel.view.*

/**
 * The adapter class for carousel container
 */
class CarouselViewPagerAdapter : RecyclerView.Adapter<CarouselViewPagerAdapter.ViewHolder>() {

    private val items: MutableList<PhotoItem> = mutableListOf()

    @Suppress("NotifyDataSetChanged")
    fun setItems(newItems: List<PhotoItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_carousel, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            Glide.with(holder.containerView)
                .load(items[position].url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .transform(CenterCrop())
                .into(holder.containerView.contentImage)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
