package com.retroblade.portfolioprod.feature.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An entity class contains the information about the photoset
 */
@Serializable
class PhotosetEntity(

    @SerialName("id")
    val id: String,

    @SerialName("owner")
    val owner: String,

    @SerialName("ownername")
    val ownerName: String,

    @SerialName("photo")
    val photos: List<PhotoEntity>,
)