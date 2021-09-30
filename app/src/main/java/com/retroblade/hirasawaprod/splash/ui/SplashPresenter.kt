package com.retroblade.hirasawaprod.splash.ui

import com.retroblade.hirasawaprod.base.BasePresenter
import com.retroblade.hirasawaprod.common.PreferenceManager
import moxy.InjectViewState
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
@InjectViewState
class SplashPresenter @Inject constructor(
    private val preferenceManager: PreferenceManager
) : BasePresenter<SplashView>() {

    override fun onFirstViewAttach() {
        loadData()
    }

    fun loadData() {
        if (preferenceManager.wasSplashShown()) {
            viewState.navigateToContent()
        } else {
            viewState.startSplash()
        }
    }

    fun onSplashShown() {
        preferenceManager.setSplashShown(true)
    }
}