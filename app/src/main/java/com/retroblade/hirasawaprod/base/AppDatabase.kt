package com.retroblade.hirasawaprod.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.retroblade.hirasawaprod.content.data.ContentDao
import com.retroblade.hirasawaprod.content.data.entity.db.PhotoEntityDb

/**
 * @author m.a.kovalev
 */
@Database(entities = arrayOf(PhotoEntityDb::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contentDao(): ContentDao
}