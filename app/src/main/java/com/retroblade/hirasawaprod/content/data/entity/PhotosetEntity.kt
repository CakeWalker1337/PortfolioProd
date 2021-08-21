package com.retroblade.hirasawaprod.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
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