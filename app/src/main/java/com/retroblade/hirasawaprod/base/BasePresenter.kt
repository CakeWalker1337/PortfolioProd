package com.retroblade.hirasawaprod.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter

/**
 * @author m.a.kovalev
 */
open class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.disposeOnFinish() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}