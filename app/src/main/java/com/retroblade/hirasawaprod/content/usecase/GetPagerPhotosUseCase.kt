package com.retroblade.hirasawaprod.content.usecase

import com.retroblade.hirasawaprod.BuildConfig
import com.retroblade.hirasawaprod.content.data.ContentRepository
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.mapper.toDb
import com.retroblade.hirasawaprod.content.mapper.toDomain
import com.retroblade.hirasawaprod.utils.NetworkManager
import com.retroblade.hirasawaprod.utils.exceptions.InvalidCacheException
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
/**
 * @author m.a.kovalev
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

    private fun getPhotosFromServer(): Single<List<Photo>> {
        return repository.getPhotosByPhotosetId(BuildConfig.API_CAROUSEL_PHOTOSET_ID)
            .take(MAX_PHOTOS_COUNT)
            .map { photo -> photo.toDomain() }
            .toList()
            .doAfterSuccess { photos ->
                repository.updateCache(
                    PhotoType.PAGER,
                    photos.map { it.toDb(PhotoType.PAGER) }
                )
            }
            .doOnError { Timber.e(it) }
            .onErrorReturnItem(emptyList())
    }

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

    private companion object {
        const val MAX_PHOTOS_COUNT = 4L
    }
}
