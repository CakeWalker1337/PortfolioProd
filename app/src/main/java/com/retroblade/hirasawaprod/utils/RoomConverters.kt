package com.retroblade.hirasawaprod.utils

import androidx.room.TypeConverter
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType

/**
 * @author m.a.kovalev
 */
class RoomConverters {

    @TypeConverter
    fun toPhotoType(value: String): PhotoType = PhotoType.from(value)

    @TypeConverter
    fun fromPhotoType(photoType: PhotoType): String = photoType.value
}