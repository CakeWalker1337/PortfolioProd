package com.retroblade.hirasawaprod.splash.ui

import com.retroblade.hirasawaprod.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * View interface represents some methods for viewer screen
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SplashView : BaseView {

    /**
     * Navigates to content screen
     */
    fun navigateToContent()

    /**
     * Starts splash screen animation
     */
    fun startSplash()
}