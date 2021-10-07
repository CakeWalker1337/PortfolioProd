package com.retroblade.hirasawaprod.content.di.module

import com.retroblade.hirasawaprod.common.di.DaoProvider
import com.retroblade.hirasawaprod.common.di.RetrofitProvider
import com.retroblade.hirasawaprod.content.data.ContentDao
import com.retroblade.hirasawaprod.content.data.ContentService
import com.retroblade.hirasawaprod.content.di.ContentScope
import dagger.Module
import dagger.Provides

/**
 * @author m.a.kovalev
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