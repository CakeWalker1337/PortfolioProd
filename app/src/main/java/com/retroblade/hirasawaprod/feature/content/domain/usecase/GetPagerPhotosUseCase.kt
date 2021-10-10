package com.retroblade.hirasawaprod.feature.content.domain.usecase

import com.retroblade.hirasawaprod.BuildConfig
import com.retroblade.hirasawaprod.feature.content.data.ContentRepository
import com.retroblade.hirasawaprod.feature.content.data.entity.db.PhotoType
import com.retroblade.hirasawaprod.feature.content.domain.Photo
import com.retroblade.hirasawaprod.feature.content.domain.mapper.toDb
import com.retroblade.hirasawaprod.feature.content.domain.mapper.toDomain
import com.retroblade.hirasawaprod.utils.NetworkManager
import com.retroblade.hirasawaprod.utils.exceptions.InvalidCacheException
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
import javax.inject.Inject

/**
 * Retrieves photos for pager carousel. The source depends on network connection
 */
@ExperimentalSerializationApi
class GetPagerPhotosUseCase @Inject constructor(
    private val repository: ContentRepository,
    private val networkManager: NetworkManager,
) {

    operator fun invoke(): Single<List<Photo>> {
        return if (networkManager.isNetworkAvailable()) {
            getPhotosFromServer()
        } else {
            getCachedPhotos()
        }
    }

    /**
     * Gets photos from server and updates cache. If there is an error happens while receiving data, it gets cached data.
     * @return single, that represents list of domain photo oblects
     */
    private fun getPhotosFromServer(): Single<List<Photo>> {
        return repository.getPhotosByPhotosetId(BuildConfig.API_CAROUSEL_PHOTOSET_ID)
            .map { photo -> photo.toDomain() }
            .toList()
            .doAfterSuccess { photos ->
                repository.updateCache(
                    PhotoType.PAGER,
                    photos.map { it.toDb(PhotoType.PAGER) }
                )
            }
            .doOnError { Timber.e(it) }
            .onErrorResumeNext { getCachedPhotos() }
    }

    /**
     * Retrieves photos from cache.
     * @return single, that represents the list of domain photo objects
     * @throws InvalidCacheException if cache is empty
     */
    private fun getCachedPhotos(): Single<List<Photo>> {
        return repository.isCacheActual(PhotoType.PAGER)
            .flatMap { isActual ->
                if (isActual) {
                    repository.getAllPhotosFromCache(PhotoType.PAGER)
                        .map { it.toDomain() }
                        .toList()
                } else throw InvalidCacheException("Cache is invalid")
            }
    }
}
