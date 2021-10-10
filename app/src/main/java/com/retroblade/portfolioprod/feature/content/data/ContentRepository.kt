package com.retroblade.portfolioprod.feature.content.data

import com.retroblade.portfolioprod.feature.content.data.entity.PhotoEntity
import com.retroblade.portfolioprod.feature.content.data.entity.db.PhotoEntityDb
import com.retroblade.portfolioprod.feature.content.data.entity.db.PhotoType
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.functions.Functions
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
import javax.inject.Inject


/**
 * A repository class contains methods for accessing content data on data layer
 */
class ContentRepository @Inject constructor(
    private val contentDao: ContentDao,
    private val contentService: ContentService
) {

    /**
     * Retrieves all photos from server
     */
    @ExperimentalSerializationApi
    fun getAllPhotosFromServer(): Observable<PhotoEntity> {
        return contentService.getAllPhotos()
            .map { it.photosContainer.photos }
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    /**
     * Retrieves all photos from cache by [photoType]
     */
    @ExperimentalSerializationApi
    fun getAllPhotosFromCache(photoType: PhotoType): Observable<PhotoEntityDb> {
        return contentDao.getCachedPhotos(photoType)
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    /**
     * Retrieves photos from server by photoset [id]
     */
    @ExperimentalSerializationApi
    fun getPhotosByPhotosetId(id: String): Observable<PhotoEntity> {
        return contentService.getPhotosByPhotosetId(id)
            .map { it.photoset.photos }
            .flattenAsObservable(Functions.identity())
            .doOnError(Timber::e)
    }

    /**
     * Checks if cache is actual for provided [photoType]
     */
    fun isCacheActual(photoType: PhotoType): Single<Boolean> {
        return contentDao.checkCacheActual(photoType)
            .map { it > 0 }
    }

    /**
     * Updates cache with new [photos] for passed [photoType]
     */
    fun updateCache(photoType: PhotoType, photos: List<PhotoEntityDb>) {
        contentDao.clearCache(photoType)
        contentDao.cachePhotos(photos)
    }
}