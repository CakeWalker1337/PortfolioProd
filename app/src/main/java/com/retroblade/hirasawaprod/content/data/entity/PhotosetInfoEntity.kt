package com.retroblade.hirasawaprod.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class PhotosetInfoEntity(

    @SerialName("id")
    val id: String,

    @SerialName("owner")
    val owner: String,

    @SerialName("title")
    val title: TextFieldEntity
)