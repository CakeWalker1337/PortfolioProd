package com.retroblade.hirasawaprod.common.di

import android.content.Context
import toothpick.config.Module

/**
 * @author m.a.kovalev
 */
class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(DaoProvider::class.java).singleton()
        bind(RetrofitProvider::class.java).singleton()
    }
}