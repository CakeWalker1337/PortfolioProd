package com.retroblade.portfolioprod.feature.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A wrapper model for photoset entity
 */
@Serializable
class ContentPhotosetEntity(

    @SerialName("photoset")
    val photoset: PhotosetEntity
)