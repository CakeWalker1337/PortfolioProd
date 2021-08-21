package com.retroblade.hirasawaprod.content.usecase

import com.retroblade.hirasawaprod.content.data.ContentRepository
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.mapper.toDomain
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @author m.a.kovalev
 */
/**
 * @author m.a.kovalev
 */
@ExperimentalSerializationApi
class GetPagerPhotosUseCase(private val repository: ContentRepository) {

    operator fun invoke(): Single<List<Photo>> {
        return repository.getAllPhotosets().subscribeOn(Schedulers.io())
            //.filter { it.title.content.lowercase() == BuildConfig.API_CAROUSEL_PHOTOSET_NAME }
            .firstOrError()
            .flatMap { photosetInfo ->
                repository.getPhotosByPhotosetId(photosetInfo.id)
                    .map { photo -> photo.toDomain() }
                    .toList()
            }
            .onErrorReturnItem(emptyList())
    }
}