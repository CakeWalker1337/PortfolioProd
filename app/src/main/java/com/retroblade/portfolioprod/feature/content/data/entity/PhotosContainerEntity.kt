package com.retroblade.portfolioprod.feature.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A wrapper entity class contains the metadata of the set of photos data
 */
@Serializable
class PhotosContainerEntity(

    @SerialName("page")
    val page: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("perpage")
    val photosPerPage: Int,

    @SerialName("total")
    val total: Int,

    @SerialName("photo")
    val photos: List<PhotoEntity>
)