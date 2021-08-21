package com.retroblade.hirasawaprod.content.data

import com.retroblade.hirasawaprod.content.data.entity.ContentPhotosEntity
import com.retroblade.hirasawaprod.content.data.entity.ContentPhotosetEntity
import com.retroblade.hirasawaprod.content.data.entity.ContentPhotosetsEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author m.a.kovalev
 */
interface ContentService {

    @GET("?method=flickr.people.getPublicPhotos")
    fun getAllPhotos(): Single<ContentPhotosEntity>

    @GET("?method=flickr.photosets.getList")
    fun getAllPhotosets(): Single<ContentPhotosetsEntity>

    @GET("?method=flickr.photosets.getPhotos")
    fun getPhotosByPhotosetId(@Query("photoset_id") id: String): Single<ContentPhotosetEntity>
}