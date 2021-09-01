package com.retroblade.hirasawaprod.splash

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Router
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.Screens
import com.retroblade.hirasawaprod.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.ktp.delegate.inject

/**
 * @author m.a.kovalev
 */
class SplashFragment : BaseFragment(), SplashView {

    private val router: Router by inject<Router>()

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter(): SplashPresenter = scope.getInstance(SplashPresenter::class.java)

    override fun getLayoutRes(): Int = R.layout.fragment_splash

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scope.inject(this)
    }

    override fun navigateToContent() {
        presenter.onSplashShown()
        router.navigateTo(Screens.Content())
    }

    override fun startSplash() {
        tvUnder?.text = "Hello"
        tvUnder?.animateBlink(500L, 2000L) {
            tvCenter?.text = "I am Hirasawa"
            tvCenter?.animateBlink(500L, 5000L)
            tvUnder?.text = "An artist with more than 4 years of experience"
            tvUnder?.animateBlink(2000L, 3500L) {
                tvCenter?.text = "I really enjoy drawing"
                tvCenter?.animateBlink(500L, 5500L)
                tvUnderLeft?.text = "And hope you "
                tvUnderLeft?.animateBlink(2500L, 3500L)
                tvUnder?.text = "enjoy"
                tvUnder?.animateBlink(2500L, 6000L) {
                    navigateToContent()
                }
                tvUnderRight?.text = " my works too"
                tvUnderRight?.animateBlink(2500L, 3500L)
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

    companion object {
        private const val TRANSITION_DURATION = 1000L

        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }
}