package com.retroblade.hirasawaprod.content.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class PhotoEntity(

    @SerialName("id")
    val id: String,

    @SerialName("owner")
    val owner: String,

    @SerialName("title")
    val title: String,

    @SerialName("dateupload")
    val uploadDate: String,

    @SerialName("count_faves")
    val likesCount: Int,

    @SerialName("count_views")
    val viewsCount: String,

    @SerialName("url_o")
    val photoUrl: String? = null
) {
}