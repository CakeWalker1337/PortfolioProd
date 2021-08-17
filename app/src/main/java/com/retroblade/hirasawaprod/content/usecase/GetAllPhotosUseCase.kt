package com.retroblade.hirasawaprod.content.usecase

import com.retroblade.hirasawaprod.content.ContentRepository
import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.mapper.toDomain
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @author m.a.kovalev
 */
@ExperimentalSerializationApi
class GetAllPhotosUseCase(private val repository: ContentRepository) {

    operator fun invoke(): Single<List<Photo>> {
        return repository.getAllPhotos()
            .map { it.toDomain() }
            .sorted { photo1, photo2 -> (photo2.uploadDate - photo1.uploadDate).toInt() }
            .toList()
    }
}