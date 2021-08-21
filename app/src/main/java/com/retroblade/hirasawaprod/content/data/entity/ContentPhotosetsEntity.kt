package com.retroblade.hirasawaprod.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class ContentPhotosetsEntity(

    @SerialName("photosets")
    val photosContainer: PhotosetsContainerEntity
)