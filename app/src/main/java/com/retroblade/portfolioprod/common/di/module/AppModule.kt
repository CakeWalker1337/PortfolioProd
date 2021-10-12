package com.retroblade.portfolioprod.common.di.module

import com.retroblade.portfolioprod.common.data.CommonPhotoCacheManager
import com.retroblade.portfolioprod.common.navigation.NavigatorHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module contains provided instances of dependencies represented in app component
 */
@Module
class AppModule() {

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = NavigatorHolder()

    @Provides
    @Singleton
    fun provideCommonPhotoCacheManager(): CommonPhotoCacheManager = CommonPhotoCacheManager()
}