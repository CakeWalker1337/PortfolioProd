package com.retroblade.hirasawaprod.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class PhotosetsContainerEntity(
    @SerialName("page")
    val page: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("perpage")
    val photosPerPage: Int,

    @SerialName("total")
    val total: Int,

    @SerialName("photoset")
    val photosets: List<PhotosetInfoEntity>
)
