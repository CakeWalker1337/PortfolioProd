package com.retroblade.portfolioprod.feature.splash.ui

import com.retroblade.portfolioprod.base.BaseView
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