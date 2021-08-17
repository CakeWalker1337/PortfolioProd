package com.retroblade.hirasawaprod.content.domain

/**
 * @author m.a.kovalev
 */
data class Photo(
    val id: String,
    val title: String,
    val uploadDate: Long,
    val likesCount: Int,
    val viewsCount: Int,
    val photoUrl: String
)