package com.retroblade.hirasawaprod.feature.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A wrapper model for photos container
 */
@Serializable
class ContentPhotosEntity(

    @SerialName("photos")
    val photosContainer: PhotosContainerEntity
)