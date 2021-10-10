package com.retroblade.hirasawaprod

import android.app.Application
import com.retroblade.hirasawaprod.common.di.component.AppComponent
import com.retroblade.hirasawaprod.common.di.component.DaggerAppComponent
import timber.log.Timber


/**
 * Custom app class. Contains basic components initialization.
 */
class App : Application() {

    /**
     * App component instance. It uses lazy initialization to be initialized only when it's called for the first time
     */
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the dependency graph
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    /**
     * Initialize Timber logger tree
     */
    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
