package com.retroblade.hirasawaprod.utils.ui

import android.view.View
import android.view.ViewPropertyAnimator
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class AnimationManager @Inject constructor() {

    fun startAnimation(view: View, animationPreset: AnimationPreset, callback: (() -> Unit)? = null) {
        animationPreset.getAnimator(view).withEndAction { callback?.invoke() }.start()
    }
}

fun interface AnimationPreset {

    fun getAnimator(view: View): ViewPropertyAnimator
}
