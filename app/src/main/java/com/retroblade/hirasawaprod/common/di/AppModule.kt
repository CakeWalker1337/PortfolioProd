package com.retroblade.hirasawaprod.common.di

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.retroblade.hirasawaprod.common.CommonPhotoCacheManager
import com.retroblade.hirasawaprod.utils.NetworkManager
import com.retroblade.hirasawaprod.utils.ui.AnimationManager
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

        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())
    }
}