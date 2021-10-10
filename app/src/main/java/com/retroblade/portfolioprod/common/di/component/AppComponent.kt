package com.retroblade.portfolioprod.common.di.component

import android.content.Context
import com.retroblade.portfolioprod.MainActivity
import com.retroblade.portfolioprod.common.CommonPhotoCacheManager
import com.retroblade.portfolioprod.common.di.module.AppModule
import com.retroblade.portfolioprod.common.navigation.NavigatorHolder
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component for app-level dependencies. Represents the dependencies provided into another components.
 * The lifetime of this component is "Singleton", which means that dependencies will be the same in each component,
 * where app component will be used in.
 */
@Component(
    modules = [
        AppModule::class
    ]
)
@Singleton
interface AppComponent {

    fun context(): Context

    fun navigatorHolder(): NavigatorHolder

    fun commonPhotoCacheManager(): CommonPhotoCacheManager

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}