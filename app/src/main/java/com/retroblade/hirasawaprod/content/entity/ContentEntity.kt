package com.retroblade.hirasawaprod.content.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class ContentEntity(

    @SerialName("photos")
    val photosContainer: PhotosContainerEntity
)