package com.retroblade.hirasawaprod.base

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * A base view interface represents some base methods for each view
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView {

    /**
     * Show toast with error [message].
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToastError(message: String)

    /**
     * Show alert dialog with error [message].
     */
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showAlertError(message: String)
}