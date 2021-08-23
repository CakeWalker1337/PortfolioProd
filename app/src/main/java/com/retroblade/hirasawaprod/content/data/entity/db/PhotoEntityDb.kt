package com.retroblade.hirasawaprod.content.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author m.a.kovalev
 */
@Entity(tableName = "photos")
class PhotoEntityDb(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "type")
    val type: PhotoType,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "count_faves")
    val likesCount: Int,

    @ColumnInfo(name = "count_views")
    val viewsCount: Int,

    @ColumnInfo(name = "upload_date")
    val uploadDate: String,

    @ColumnInfo(name = "url")
    val url: String,

    )