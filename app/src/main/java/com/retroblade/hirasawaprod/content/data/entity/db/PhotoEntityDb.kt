package com.retroblade.hirasawaprod.content.data.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * @author m.a.kovalev
 */
@Entity(tableName = "photos", primaryKeys = ["id", "type"])
class PhotoEntityDb(

    @ColumnInfo(name = "id")
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
    val url: String
)