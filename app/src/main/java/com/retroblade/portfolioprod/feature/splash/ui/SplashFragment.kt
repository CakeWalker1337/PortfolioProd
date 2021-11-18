package com.retroblade.portfolioprod.feature.splash.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import com.retroblade.portfolioprod.App
import com.retroblade.portfolioprod.BuildConfig
import com.retroblade.portfolioprod.R
import com.retroblade.portfolioprod.base.BaseFragment
import com.retroblade.portfolioprod.common.navigation.ContentScreen
import com.retroblade.portfolioprod.common.navigation.NavigatorHolder
import com.retroblade.portfolioprod.common.ui.CompositeAnimationItem
import com.retroblade.portfolioprod.common.ui.CompositeAnimationManager
import com.retroblade.portfolioprod.feature.splash.di.component.DaggerSplashComponent
import com.retroblade.portfolioprod.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_splash.*
import moxy.ktx.moxyPresenter
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Provider

/**
 * A fragment for showing splash screen. Shows animated sequence of labels and introduces the application to user.
 */
class SplashFragment : BaseFragment(), SplashView {

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun getLayoutRes(): Int = R.layout.fragment_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        // component injector must be called before onCreate. Extremely necessary to use exact call sequence in fragments
        val appComponent = (requireContext().applicationContext as App).appComponent
        DaggerSplashComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // sets dark text color for status bar and system navbar
        if (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
            activity?.window?.decorView?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }
    }

    override fun navigateToContent() {
        presenter.handleOnSplashShown()
        navigatorHolder.getNavigator()?.executeNavigation(ContentScreen)
    }

    override fun startSplash() {
        val years = DateUtils.getYearsTillNow(BuildConfig.EXPERIENCE_START_DATE)
        CompositeAnimationManager.Builder()
            .withFadeInAndOutTime(TRANSITION_DURATION, TRANSITION_DURATION)
            .addItem(CompositeAnimationItem(WeakReference(tvHello), getString(R.string.splash_text_frame_1), 500L, 4000L))
            .addItem(CompositeAnimationItem(WeakReference(tvCenter), getString(R.string.splash_text_frame_2_1), 4500L, 11500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnder), getString(R.string.splash_text_frame_2_2, years), 6500L, 11500L))
            .addItem(CompositeAnimationItem(WeakReference(tvCenter), getString(R.string.splash_text_frame_3_1), 12000L, 18500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnderLeft), getString(R.string.splash_text_frame_3_2_1), 14000L, 18500L))
            .addItem(CompositeAnimationItem(WeakReference(tvUnder), getString(R.string.splash_text_frame_3_2_2), 14000L, 21500L) { navigateToContent() })
            .addItem(CompositeAnimationItem(WeakReference(tvUnderRight), getString(R.string.splash_text_frame_3_2_3), 14000L, 18500L))
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