package com.retroblade.portfolioprod.feature.content.di.module

import com.retroblade.portfolioprod.common.di.DaoProvider
import com.retroblade.portfolioprod.common.di.RetrofitProvider
import com.retroblade.portfolioprod.feature.content.data.ContentDao
import com.retroblade.portfolioprod.feature.content.data.ContentService
import com.retroblade.portfolioprod.feature.content.di.ContentScope
import dagger.Module
import dagger.Provides

/**
 * Dagger module contains provided instances of dependencies represented in content component
 */
@Module
class ContentModule {

    @Provides
    @ContentScope
    fun provideContentService(retrofitProvider: RetrofitProvider): ContentService {
        return retrofitProvider.contentService
    }

    @Provides
    @ContentScope
    fun provideContentDao(daoProvider: DaoProvider): ContentDao {
        return daoProvider.contentDao
    }
}