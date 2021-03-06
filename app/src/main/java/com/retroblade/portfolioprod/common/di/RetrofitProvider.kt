package com.retroblade.portfolioprod.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.retroblade.portfolioprod.BuildConfig
import com.retroblade.portfolioprod.feature.content.data.ContentService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject

/**
 * A provider class for retrieving instances of api services
 */
class RetrofitProvider @Inject constructor() {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                // adding parameters that will be passed in EACH request
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