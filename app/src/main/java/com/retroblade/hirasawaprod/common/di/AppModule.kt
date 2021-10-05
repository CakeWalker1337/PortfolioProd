package com.retroblade.hirasawaprod.common.di

import android.content.Context
import com.retroblade.hirasawaprod.common.CommonPhotoCacheManager
import com.retroblade.hirasawaprod.common.navigation.NavigatorHolder
import com.retroblade.hirasawaprod.common.ui.AnimationManager
import com.retroblade.hirasawaprod.utils.NetworkManager
import toothpick.config.Module

/**
 * @author m.a.kovalev
 */
class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(DaoProvider::class.java).singleton()
        bind(RetrofitProvider::class.java).singleton()
        bind(NetworkManager::class.java).singleton()
        bind(CommonPhotoCacheManager::class.java).singleton()
        bind(AnimationManager::class.java).singleton()

        bind(NavigatorHolder::class.java).singleton()
    }
}