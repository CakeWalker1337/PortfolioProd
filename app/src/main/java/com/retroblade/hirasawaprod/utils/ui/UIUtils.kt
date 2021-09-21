package com.retroblade.hirasawaprod.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

/**
 * @author m.a.kovalev
 */
fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(animation: Animator?) {
            endFakeDrag()
        }

        override fun onAnimationCancel(animation: Animator?) = Unit

        override fun onAnimationRepeat(animation: Animator?) = Unit

    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}

fun Snackbar.setTextViewParams(block: TextView.() -> Unit): Snackbar {
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.apply(block)
    return this
}

fun Context.dpToPx(dp: Float): Int = (dp * this.resources.displayMetrics.density).roundToInt()

fun View.dpToPx(dp: Float): Int = this.context.dpToPx(dp)