package com.retroblade.hirasawaprod.feature.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A photo entity model. It contains full retrieved information about photo
 */
@Serializable
class PhotoEntity(

    @SerialName("id")
    val id: String,

    @SerialName("owner")
    val owner: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("dateupload")
    val uploadDate: String,

    @SerialName("count_faves")
    val likesCount: Int,

    @SerialName("count_views")
    val viewsCount: String,

    @SerialName("url_l")
    val photoUrl: String? = null
) {
}