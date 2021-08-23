package com.retroblade.hirasawaprod.content.di

import com.retroblade.hirasawaprod.common.di.DaoProvider
import com.retroblade.hirasawaprod.common.di.RetrofitProvider
import com.retroblade.hirasawaprod.content.ContentItemsFactory
import com.retroblade.hirasawaprod.content.data.ContentDao
import com.retroblade.hirasawaprod.content.data.ContentService
import toothpick.config.Module
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class ContentModule @Inject constructor(
    daoProvider: DaoProvider,
    retrofitProvider: RetrofitProvider
) : Module() {

    init {
        bind(ContentDao::class.java).toInstance(daoProvider.contentDao)
        bind(ContentService::class.java).toInstance(retrofitProvider.contentService)
        bind(ContentItemsFactory::class.java)
    }
}