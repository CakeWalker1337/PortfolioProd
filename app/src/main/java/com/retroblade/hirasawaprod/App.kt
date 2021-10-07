package com.retroblade.hirasawaprod

import android.app.Application
import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.common.di.component.DaggerAppComponent
import timber.log.Timber


/**
 * @author m.a.kovalev
 */
class App : Application() {

    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

const val APP_SCOPE = "APP_SCOPE"