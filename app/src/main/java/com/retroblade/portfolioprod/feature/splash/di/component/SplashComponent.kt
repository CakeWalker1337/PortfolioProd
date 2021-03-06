package com.retroblade.portfolioprod.feature.splash.di.component

import com.retroblade.portfolioprod.common.di.component.AppComponent
import com.retroblade.portfolioprod.feature.splash.di.SplashScope
import com.retroblade.portfolioprod.feature.splash.di.module.SplashModule
import com.retroblade.portfolioprod.feature.splash.ui.SplashFragment
import dagger.Component

/**
 * Dagger component for splash feature. Represents the dependencies provided into another components
 * Actually, this component doesn't expose anything to other components
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