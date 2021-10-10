package com.retroblade.portfolioprod.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Loads full image into current view by image [url]
 */
fun ImageView.loadFullImage(url: String) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        .dontTransform()

    Glide.with(this)
        .load(url)
        .apply(RequestOptions().apply(options))
        .into(this)
}

/**
 * Loads default-sized image into current view by image [url]
 */
fun ImageView.loadCompressedImage(url: String) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}