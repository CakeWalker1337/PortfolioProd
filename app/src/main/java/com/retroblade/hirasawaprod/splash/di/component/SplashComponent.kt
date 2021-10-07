package com.retroblade.hirasawaprod.splash.di.component

import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.splash.di.SplashScope
import com.retroblade.hirasawaprod.splash.di.module.SplashModule
import com.retroblade.hirasawaprod.splash.ui.SplashFragment
import dagger.Component

/**
 * @author m.a.kovalev
 */
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        SplashModule::class
    ]
)
@SplashScope
interface SplashComponent {

    fun inject(fragment: SplashFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SplashComponent
    }
}