package com.retroblade.hirasawaprod

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * @author m.a.kovalev
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}