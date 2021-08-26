package com.retroblade.hirasawaprod.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object GlideImageHelper {

    fun loadFlickrFull(url: String, image: ImageView) {
        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .dontTransform()

        Glide.with(image)
            .load(url)
            .apply(RequestOptions().apply(options))
            .into(image)
    }

    fun clear(view: ImageView) {
        Glide.with(view).clear(view)
        view.setImageDrawable(null)
    }

    interface LoadingListener {
        fun onSuccess()
        fun onError()
    }

    private class RequestListenerWrapper<T> internal constructor(private val listener: LoadingListener?) :
        RequestListener<T> {
        override fun onResourceReady(
            resource: T, model: Any, target: Target<T>,
            dataSource: DataSource, isFirstResource: Boolean
        ): Boolean {
            listener?.onSuccess()
            return false
        }

        override fun onLoadFailed(
            ex: GlideException?, model: Any,
            target: Target<T>, isFirstResource: Boolean
        ): Boolean {
            listener?.onError()
            return false
        }
    }
}