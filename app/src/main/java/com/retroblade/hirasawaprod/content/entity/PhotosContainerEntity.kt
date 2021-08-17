package com.retroblade.hirasawaprod.content.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
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