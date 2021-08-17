package com.retroblade.hirasawaprod.content

import com.retroblade.hirasawaprod.content.entity.ContentEntity
import io.reactivex.Single
import retrofit2.http.GET


/**
 * @author m.a.kovalev
 */
interface ContentService {

    @GET("?method=flickr.people.getPublicPhotos")
    fun getAllPhotos(): Single<ContentEntity>
}