package com.retroblade.hirasawaprod.content.mapper

import com.retroblade.hirasawaprod.content.data.entity.PhotoEntity
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoEntityDb
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import com.retroblade.hirasawaprod.content.domain.Photo
import org.joda.time.DateTime

/**
 * @author m.a.kovalev
 */

fun PhotoEntity.toDomain(): Photo =
    Photo(
        id = this.id,
        title = this.title,
        uploadDate = DateTime(this.uploadDate.toLong()),
        likesCount = this.likesCount,
        viewsCount = this.viewsCount.toInt(),
        photoUrl = this.photoUrl ?: "",
    )

fun PhotoEntityDb.toDomain(): Photo =
    Photo(
        id = this.id,
        title = this.title,
        uploadDate = DateTime(this.uploadDate.toLong()),
        likesCount = this.likesCount,
        viewsCount = this.viewsCount,
        photoUrl = this.url,
    )

fun Photo.toDb(type: PhotoType): PhotoEntityDb =
    PhotoEntityDb(
        id = this.id,
        type = type,
        title = this.title,
        uploadDate = this.uploadDate.toString(),
        likesCount = this.likesCount,
        viewsCount = this.viewsCount,
        url = this.photoUrl
    )