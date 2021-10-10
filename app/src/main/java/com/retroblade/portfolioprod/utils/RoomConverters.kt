package com.retroblade.portfolioprod.utils

import androidx.room.TypeConverter
import com.retroblade.portfolioprod.feature.content.data.entity.db.PhotoType

/**
 * Util class contains converters for entities used in room
 */
class RoomConverters {

    @TypeConverter
    fun toPhotoType(value: String): PhotoType = PhotoType.from(value)

    @TypeConverter
    fun fromPhotoType(photoType: PhotoType): String = photoType.value
}