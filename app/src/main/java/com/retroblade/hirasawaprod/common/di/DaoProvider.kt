package com.retroblade.hirasawaprod.common.di

import android.content.Context
import androidx.room.Room
import com.retroblade.hirasawaprod.base.AppDatabase
import com.retroblade.hirasawaprod.content.data.ContentDao
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class DaoProvider @Inject constructor(context: Context) {

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "hirasawa-db").build()

    val contentDao: ContentDao
        get() = db.contentDao()
}