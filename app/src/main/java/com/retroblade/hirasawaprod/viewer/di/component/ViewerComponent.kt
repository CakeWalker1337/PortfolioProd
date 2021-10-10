package com.retroblade.hirasawaprod.viewer.di.component

import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.splash.di.SplashScope
import com.retroblade.hirasawaprod.viewer.di.module.ViewerModule
import com.retroblade.hirasawaprod.viewer.ui.ViewerActivity
import dagger.Component

/**
 * Dagger component for viewer feature. Represents the dependencies provided into another components
 * Actually, this component doesn't expose anything to other components
 */
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        ViewerModule::class
    ]
)
@SplashScope
interface ViewerComponent {

    fun inject(activity: ViewerActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ViewerComponent
    }
}