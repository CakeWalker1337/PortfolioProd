package com.retroblade.hirasawaprod.common.di

import com.retroblade.hirasawaprod.content.di.ContentModule
import toothpick.Scope
import toothpick.config.Module
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class ModuleHolder @Inject constructor() : Module() {

    init {
        bind(ContentModule::class.java).singleton()
    }

    inline fun <reified T> getModule(scope: Scope): T {
        return scope.getInstance(T::class.java)
    }
}