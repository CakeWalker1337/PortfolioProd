package com.retroblade.hirasawaprod.splash.ui

import com.retroblade.hirasawaprod.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author m.a.kovalev
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SplashView : BaseView {

    fun navigateToContent()

    fun startSplash()
}