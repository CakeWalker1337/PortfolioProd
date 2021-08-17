package com.retroblade.hirasawaprod.content

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.retroblade.hirasawaprod.BuildConfig
import com.retroblade.hirasawaprod.content.entity.PhotoEntity
import io.reactivex.Observable
import io.reactivex.internal.functions.Functions
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * @author m.a.kovalev
 */
class ContentRepository {
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
    private val service = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/services/rest/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType())
        )

        .client(httpClient)
        .build().create(ContentService::class.java)

    @ExperimentalSerializationApi
    fun getAllPhotos(): Observable<PhotoEntity> {
        return service.getAllPhotos()
            .map { it.photosContainer.photos }
            .flattenAsObservable(Functions.identity())
    }

}