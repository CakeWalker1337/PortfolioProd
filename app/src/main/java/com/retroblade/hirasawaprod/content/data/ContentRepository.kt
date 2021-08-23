package com.retroblade.hirasawaprod.content.data

import com.retroblade.hirasawaprod.content.data.entity.PhotoEntity
import com.retroblade.hirasawaprod.content.data.entity.PhotosetInfoEntity
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoEntityDb
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.functions.Functions
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


/**
 * @author m.a.kovalev
 */
class ContentRepository @Inject constructor(
    private val contentDao: ContentDao,
    private val contentService: ContentService
) {
    @ExperimentalSerializationApi
    fun getAllPhotosFromServer(): Observable<PhotoEntity> {
        return contentService.getAllPhotos()
            .map { it.photosContainer.photos }
            .flattenAsObservable(Functions.identity())
    }

    @ExperimentalSerializationApi
    fun getAllPhotosFromCache(photoType: PhotoType): Observable<PhotoEntityDb> {
        return contentDao.getCachedPhotos(photoType)
            .flattenAsObservable(Functions.identity())
    }

    @ExperimentalSerializationApi
    fun getAllPhotosets(): Observable<PhotosetInfoEntity> {
        return contentService.getAllPhotosets()
            .map { it.photosContainer.photosets }
            .flattenAsObservable(Functions.identity())
    }

    @ExperimentalSerializationApi
    fun getPhotosByPhotosetId(id: String): Observable<PhotoEntity> {
        return contentService.getPhotosByPhotosetId(id)
            .map { it.photoset.photos }
            .flattenAsObservable(Functions.identity())
    }

    fun isCacheActual(): Single<Boolean> {
        return contentDao.checkCacheActual()
            .map { it > 0 }
    }

    fun updateCache(photos: List<PhotoEntityDb>) {
        contentDao.clearCache()
        contentDao.cachePhotos(photos)
    }


}