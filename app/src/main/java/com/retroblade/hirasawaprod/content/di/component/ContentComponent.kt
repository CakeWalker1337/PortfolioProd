package com.retroblade.hirasawaprod.content.di.component

import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.content.di.ContentScope
import com.retroblade.hirasawaprod.content.di.module.ContentModule
import com.retroblade.hirasawaprod.content.ui.ContentFragment
import dagger.Component

/**
 * @author m.a.kovalev
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