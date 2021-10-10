package com.retroblade.portfolioprod.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter

/**
 * A base presenter class contains base methods for any presenter class
 * Particularly holds all disposables which are being executed till the end of the lifecycle.
 */
open class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    /**
     * Stores [this] disposable till the end of the lifecycle
     */
    fun Disposable.disposeOnFinish() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}