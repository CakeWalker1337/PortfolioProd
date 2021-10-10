package com.retroblade.hirasawaprod.common.ui

import android.view.View
import android.view.ViewPropertyAnimator
import javax.inject.Inject

/**
 * A simple animation manager that performs animation by animation presets
 * @see AnimationPreset
 */
class AnimationManager @Inject constructor() {

    /**
     * Launches [animationPreset] for [view]. Calls [callback] in the end of animation
     */
    fun startAnimation(view: View, animationPreset: AnimationPreset, callback: (() -> Unit)? = null) {
        animationPreset.getAnimator(view).withEndAction { callback?.invoke() }.start()
    }
}

/**
 * An interface of animation preset that provides animator for being executed in animation manager
 * @see AnimationManager
 */
fun interface AnimationPreset {

    /**
     * Provides animator for [view]
     */
    fun getAnimator(view: View): ViewPropertyAnimator
}
