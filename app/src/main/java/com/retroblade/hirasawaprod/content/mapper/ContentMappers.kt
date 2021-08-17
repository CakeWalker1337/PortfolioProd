package com.retroblade.hirasawaprod.content.mapper

import com.retroblade.hirasawaprod.content.domain.Photo
import com.retroblade.hirasawaprod.content.entity.PhotoEntity

/**
 * @author m.a.kovalev
 */

fun PhotoEntity.toDomain(): Photo =
    Photo(
        id = this.id,
        title = this.title,
        uploadDate = this.uploadDate.toLong(),
        likesCount = this.likesCount,
        viewsCount = this.viewsCount.toInt(),
        photoUrl = this.photoUrl ?: "",
    )