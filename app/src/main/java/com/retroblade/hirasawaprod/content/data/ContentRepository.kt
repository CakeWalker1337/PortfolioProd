package com.retroblade.hirasawaprod.content.data

import com.retroblade.hirasawaprod.content.data.entity.PhotoEntity
import com.retroblade.hirasawaprod.content.data.entity.PhotosetInfoEntity
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoEntityDb
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.functions.Functions
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
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
            .doOnError(Timber::e)
    }

    @ExperimentalSerializationApi
    fun getAllPhotosFromCache(photoType: PhotoType): Observable<PhotoEntityDb> {
        return contentDao.getCachedPhotos(photoType)
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    @ExperimentalSerializationApi
    fun getAllPhotosets(): Observable<PhotosetInfoEntity> {
        return contentService.getAllPhotosets()
            .map { it.photosContainer.photosets }
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    @ExperimentalSerializationApi
    fun getPhotosByPhotosetId(id: String): Observable<PhotoEntity> {
        return contentService.getPhotosByPhotosetId(id)
            .map { it.photoset.photos }
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    fun isCacheActual(photoType: PhotoType): Single<Boolean> {
        return contentDao.checkCacheActual(photoType)
            .map { it > 0 }
    }

    fun updateCache(photoType: PhotoType, photos: List<PhotoEntityDb>) {
        contentDao.clearCache(photoType)
        contentDao.cachePhotos(photos)
    }


}