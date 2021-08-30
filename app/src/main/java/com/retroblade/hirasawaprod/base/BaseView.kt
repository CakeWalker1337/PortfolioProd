package com.retroblade.hirasawaprod.base

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author m.a.kovalev
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