package com.retroblade.hirasawaprod.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.retroblade.hirasawaprod.BuildConfig
import com.retroblade.hirasawaprod.content.data.ContentService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class RetrofitProvider @Inject constructor() {
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("extras", BuildConfig.API_CONTENT_EXTRAS)
                .addQueryParameter("user_id", BuildConfig.API_USER_ID)
                .addQueryParameter("per_page", BuildConfig.API_ELEMENTS_PER_PAGE.toString())
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build()
            val request = original.newBuilder().url(newUrl).build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()


    @ExperimentalSerializationApi
    val contentService: ContentService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
        .client(httpClient)
        .build()
        .create(ContentService::class.java)

}