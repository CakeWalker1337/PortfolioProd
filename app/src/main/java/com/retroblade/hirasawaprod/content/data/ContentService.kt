package com.retroblade.hirasawaprod.content.data

import com.retroblade.hirasawaprod.content.data.entity.ContentPhotosEntity
import com.retroblade.hirasawaprod.content.data.entity.ContentPhotosetEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * A service interface provides some methods for retrieving content data from flickr
 */
interface ContentService {

    @GET("?method=flickr.people.getPublicPhotos")
    fun getAllPhotos(): Single<ContentPhotosEntity>

    @GET("?method=flickr.photosets.getPhotos")
    fun getPhotosByPhotosetId(@Query("photoset_id") id: String): Single<ContentPhotosetEntity>
}