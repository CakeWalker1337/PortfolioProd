package com.retroblade.hirasawaprod.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.retroblade.hirasawaprod.R
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * @author m.a.kovalev
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startSplashAnimation()
    }

    private fun startSplashAnimation() {
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
                tvUnder.animateBlink(2500L, 6000L) {
                    findNavController().navigate(R.id.action_splashFragment_to_contentFragment)
                }
                tvUnderRight.text = " my works too"
                tvUnderRight.animateBlink(2500L, 3500L)
            }
        }
    }

    private fun View.animateBlink(startDelay: Long, stayTime: Long, callBack: (() -> Unit)? = null) {
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