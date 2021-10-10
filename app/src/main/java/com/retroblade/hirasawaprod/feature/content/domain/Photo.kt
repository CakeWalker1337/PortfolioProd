package com.retroblade.hirasawaprod.feature.content.domain

import org.joda.time.DateTime

/**
 * Photo domain model. Contains specific fields for domain layer
 */
data class Photo(
    val id: String,
    val title: String,
    val uploadDate: DateTime,
    val likesCount: Int,
    val viewsCount: Int,
    val photoUrl: String
)