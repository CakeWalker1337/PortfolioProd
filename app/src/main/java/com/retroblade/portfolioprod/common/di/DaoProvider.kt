package com.retroblade.portfolioprod.common.di

import android.content.Context
import androidx.room.Room
import com.retroblade.portfolioprod.BuildConfig
import com.retroblade.portfolioprod.common.data.AppDatabase
import com.retroblade.portfolioprod.feature.content.data.ContentDao
import javax.inject.Inject

/**
 * A provider for data access objects used for accessing to the database
 */
class DaoProvider @Inject constructor(context: Context) {

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DB_NAME).build()

    val contentDao: ContentDao
        get() = db.contentDao()
}