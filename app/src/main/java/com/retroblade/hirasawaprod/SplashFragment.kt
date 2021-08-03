package com.retroblade.hirasawaprod

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author m.a.kovalev
 */
class SplashFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvUnder.text = "Hello"
        tvUnder.animateBlink(500L, 2000L) {
            tvCenter.text = "I am Hirasawa"
            tvCenter.animateBlink(500L, 5000L)
            tvUnder.text = "A drawer with more than 4 years of experience"
            tvUnder.animateBlink(2000L, 3500L) {
                tvCenter.text = "I really enjoy drawing"
                tvCenter.animateBlink(500L, 5500L)
                tvUnderLeft.text = "And hope you "
                tvUnderLeft.animateBlink(2500L, 3500L)
                tvUnder.text = "enjoy"
                tvUnder.animateBlink(2500L, 6000L)
                tvUnderRight.text = " my works too"
                tvUnderRight.animateBlink(2500L, 3500L)
            }
        }
    }


    fun View.animateBlink(startDelay: Long, stayTime: Long, callBack: (() -> Unit)? = null) {
        this.animate().setStartDelay(startDelay).alpha(1.0F).setDuration(TRANSITION_DURATION)
            .withEndAction {
                this.animate().setStartDelay(stayTime).alpha(0.0F).setDuration(TRANSITION_DURATION)
                    .withEndAction {
                        callBack?.invoke()
                    }.start()
            }.start()
    }

    private companion object {
        const val TRANSITION_DURATION = 1000L
    }
}