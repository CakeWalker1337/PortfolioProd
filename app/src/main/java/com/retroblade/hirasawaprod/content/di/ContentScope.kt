package com.retroblade.hirasawaprod.content.di

import javax.inject.Scope

/**
 * A scope for content screen. Marks the lifetime of viewer dependencies
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentScope