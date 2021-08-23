package com.retroblade.hirasawaprod.content.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoEntityDb
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoType
import io.reactivex.Single

/**
 * @author m.a.kovalev
 */
@Dao
interface ContentDao {

    @Query("SELECT * FROM photos WHERE type = :type")
    fun getCachedPhotos(type: PhotoType): Single<List<PhotoEntityDb>>

    @Insert
    fun cachePhotos(photos: List<PhotoEntityDb>)

    @Query("DELETE FROM photos")
    fun clearCache()

    @Query("SELECT count(*) FROM photos")
    fun checkCacheActual(): Single<Int>
}