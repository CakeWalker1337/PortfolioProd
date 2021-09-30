package com.retroblade.hirasawaprod.splash.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Router
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.Screens
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.utils.getYearsOfExperienceTillNow
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
        if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
            activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    override fun navigateToContent() {
        presenter.onSplashShown()
        router.navigateTo(Screens.Content())
    }

    override fun startSplash() {
        tvUnder?.text = getString(R.string.text_frame_1)
        tvUnder?.animateBlink(500L, 2000L) {
            tvCenter?.text = getString(R.string.text_frame_2_1)
            tvCenter?.animateBlink(500L, 5000L)
            tvUnder?.text = getString(R.string.text_frame_2_2, getYearsOfExperienceTillNow())
            tvUnder?.animateBlink(2000L, 3500L) {
                tvCenter?.text = getString(R.string.text_frame_3_1)
                tvCenter?.animateBlink(500L, 5500L)
                tvUnderLeft?.text = getString(R.string.text_frame_3_2_1)
                tvUnderLeft?.animateBlink(2500L, 3500L)
                tvUnder?.text = getString(R.string.text_frame_3_2_2)
                tvUnder?.animateBlink(2500L, 6000L) {
                    navigateToContent()
                }
                tvUnderRight?.text = getString(R.string.text_frame_3_2_3)
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