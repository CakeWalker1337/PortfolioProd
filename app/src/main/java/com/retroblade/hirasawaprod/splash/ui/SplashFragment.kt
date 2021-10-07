package com.retroblade.hirasawaprod.splash.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.retroblade.hirasawaprod.App
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.base.BaseFragment
import com.retroblade.hirasawaprod.common.navigation.ContentScreen
import com.retroblade.hirasawaprod.common.navigation.NavigatorHolder
import com.retroblade.hirasawaprod.common.ui.CompositeAnimationItem
import com.retroblade.hirasawaprod.common.ui.CompositeAnimationManager
import com.retroblade.hirasawaprod.splash.di.component.DaggerSplashComponent
import com.retroblade.hirasawaprod.utils.getYearsOfExperienceTillNow
import kotlinx.android.synthetic.main.fragment_splash.*
import moxy.ktx.moxyPresenter
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author m.a.kovalev
 */
class SplashFragment : BaseFragment(), SplashView {

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun getLayoutRes(): Int = R.layout.fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (requireContext().applicationContext as App).appComponent
        DaggerSplashComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
            activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    override fun navigateToContent() {
        presenter.onSplashShown()
        navigatorHolder.getNavigator()?.executeNavigation(ContentScreen)
    }

    override fun startSplash() {
        val years = getYearsOfExperienceTillNow()
        CompositeAnimationManager.Builder()
            .withFadeInAndOutTime(TRANSITION_DURATION, TRANSITION_DURATION)
            .addItem(CompositeAnimationItem(WeakReference(tvHello), getString(R.string.text_frame_1), 500L, 4000L))
            .addItem(CompositeAnimationItem(WeakReference(tvCenter), getString(R.string.text_frame_2_1), 4500L, 11500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnder), getString(R.string.text_frame_2_2, years), 6500L, 11500L))
            .addItem(CompositeAnimationItem(WeakReference(tvCenter), getString(R.string.text_frame_3_1), 12000L, 18500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnderLeft), getString(R.string.text_frame_3_2_1), 14000L, 18500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnder), getString(R.string.text_frame_3_2_2), 14000L, 21500L) {
                navigateToContent()
            })
            .addItem(CompositeAnimationItem(WeakReference(tvUnderRight), getString(R.string.text_frame_3_2_3), 14000L, 18500L))
            .build()
            .startAnimation()
    }

    companion object {
        private const val TRANSITION_DURATION = 1000L

        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }
}