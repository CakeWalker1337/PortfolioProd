package com.retroblade.hirasawaprod.feature.content.di.component

import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.feature.content.di.ContentScope
import com.retroblade.hirasawaprod.feature.content.di.module.ContentModule
import com.retroblade.hirasawaprod.feature.content.ui.ContentFragment
import dagger.Component

/**
 * Dagger component for content feature. Represents the dependencies provided into another components
 * Actually, this component doesn't expose anything to other components
 */
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        ContentModule::class
    ]
)
@ContentScope
interface ContentComponent {

    fun inject(fragment: ContentFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ContentComponent
    }
}