package com.retroblade.hirasawaprod.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.retroblade.hirasawaprod.feature.content.data.ContentDao
import com.retroblade.hirasawaprod.feature.content.data.entity.db.PhotoEntityDb

/**
 * A database class that represents a database with entities and provides data access objects for them
 */
@Database(entities = [PhotoEntityDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contentDao(): ContentDao
}