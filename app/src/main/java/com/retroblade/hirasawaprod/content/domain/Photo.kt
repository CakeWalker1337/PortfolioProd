package com.retroblade.hirasawaprod.content.domain

import org.joda.time.DateTime

/**
 * @author m.a.kovalev
 */
data class Photo(

    val id: String,

    val title: String,

    val uploadDate: DateTime,

    val likesCount: Int,

    val viewsCount: Int,

    val photoUrl: String
)