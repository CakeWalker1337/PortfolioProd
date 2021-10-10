package com.retroblade.hirasawaprod.splash.ui

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.common.PreferenceManager
import moxy.InjectViewState
import javax.inject.Inject

/**
 * Presenter for splash screen. Contains business logic for loading and processing data
 */
@InjectViewState
class SplashPresenter @Inject constructor(
    private val preferenceManager: PreferenceManager
) : BasePresenter<SplashView>() {

    override fun onFirstViewAttach() {
        loadData()
    }

    /**
     * Loads splash screen data
     */
    fun loadData() {
        if (preferenceManager.wasSplashShown()) {
            viewState.navigateToContent()
        } else {
            viewState.startSplash()
        }
    }

    /**
     * Executes some logic when splash screen is shown (finished its animation)
     */
    fun handleOnSplashShown() {
        preferenceManager.setSplashShown(true)
    }
}