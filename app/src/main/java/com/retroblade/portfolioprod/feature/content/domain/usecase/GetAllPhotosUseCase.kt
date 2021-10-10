package com.retroblade.portfolioprod.feature.content.domain.usecase

import com.retroblade.portfolioprod.feature.content.data.ContentRepository
import com.retroblade.portfolioprod.feature.content.data.entity.db.PhotoType
import com.retroblade.portfolioprod.feature.content.domain.Photo
import com.retroblade.portfolioprod.feature.content.domain.mapper.toDb
import com.retroblade.portfolioprod.feature.content.domain.mapper.toDomain
import com.retroblade.portfolioprod.utils.NetworkManager
import com.retroblade.portfolioprod.utils.exceptions.InvalidCacheException
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

/**
 * Gets all photos from source. The source depends on network connection
 */
@ExperimentalSerializationApi
class GetAllPhotosUseCase @Inject constructor(
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
        return repository.getAllPhotosFromServer()
            .map { it.toDomain() }
            .sorted { photo1, photo2 -> photo2.uploadDate.compareTo(photo1.uploadDate) }
            .toList()
            .doOnSuccess { photos ->
                repository.updateCache(
                    PhotoType.CONTENT,
                    photos.map { it.toDb(PhotoType.CONTENT) }
                )
            }
            .onErrorResumeNext {
                getCachedPhotos()
            }
    }

    /**
     * Retrieves photos from cache.
     * @return single, that represents the list of domain photo objects
     * @throws InvalidCacheException if cache is empty
     */
    private fun getCachedPhotos(): Single<List<Photo>> {
        return repository.isCacheActual(PhotoType.CONTENT)
            .flatMap { isActual ->
                if (isActual) {
                    repository.getAllPhotosFromCache(PhotoType.CONTENT)
                        .map { it.toDomain() }
                        .toList()
                } else throw InvalidCacheException("Cache is invalid")
            }
    }
}