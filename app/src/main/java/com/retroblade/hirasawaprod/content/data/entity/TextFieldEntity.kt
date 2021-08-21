package com.retroblade.hirasawaprod.content.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author m.a.kovalev
 */
@Serializable
class TextFieldEntity(

    @SerialName("_content")
    val content: String
)