package com.retroblade.hirasawaprod.feature.content.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.retroblade.hirasawaprod.feature.content.data.entity.db.PhotoEntityDb
import com.retroblade.hirasawaprod.feature.content.data.entity.db.PhotoType
import io.reactivex.Single

/**
 * Content data access object. Contains requests to content database
 */
@Dao
interface ContentDao {

    @Query("SELECT * FROM photos WHERE type = :photoType")
    fun getCachedPhotos(photoType: PhotoType): Single<List<PhotoEntityDb>>

    @Insert
    fun cachePhotos(photos: List<PhotoEntityDb>)

    @Query("DELETE FROM photos WHERE type IN (:photoType, 'UNKNOWN')")
    fun clearCache(photoType: PhotoType)

    @Query("SELECT count(*) FROM photos WHERE type = :photoType")
    fun checkCacheActual(photoType: PhotoType): Single<Int>
}