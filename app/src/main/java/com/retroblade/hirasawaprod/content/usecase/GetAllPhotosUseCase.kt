package com.retroblade.hirasawaprod.content.usecase

import android.content.Context
import com.retroblade.hirasawaprod.content.data.ContentRepository
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.mapper.toDb
import com.retroblade.hirasawaprod.content.mapper.toDomain
import com.retroblade.hirasawaprod.utils.NetworkUtils
import com.retroblade.hirasawaprod.utils.exceptions.InvalidCacheException
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
@ExperimentalSerializationApi
class GetAllPhotosUseCase @Inject constructor(
    private val context: Context,
    private val repository: ContentRepository
) {

    operator fun invoke(): Single<List<Photo>> {
        return if (NetworkUtils.isNetworkAvailable(context)) {
            getPhotosFromServer()
        } else {
            getCachedPhotos()
        }
    }

    private fun getPhotosFromServer(): Single<List<Photo>> {
        return repository.getAllPhotosFromServer()
            .map { it.toDomain() }
            .sorted { photo1, photo2 -> (photo2.uploadDate - photo1.uploadDate).toInt() }
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