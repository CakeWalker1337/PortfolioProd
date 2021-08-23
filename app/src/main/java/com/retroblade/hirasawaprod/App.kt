package com.retroblade.hirasawaprod

import android.app.Application
import com.github.moxy_community.moxy.androidx.BuildConfig
import com.retroblade.hirasawaprod.common.di.AppModule
import com.retroblade.hirasawaprod.common.di.ModuleHolder
import timber.log.Timber
import toothpick.configuration.Configuration
import toothpick.ktp.KTP


/**
 * @author m.a.kovalev
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initLogger()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            KTP.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            KTP.setConfiguration(Configuration.forProduction())
        }
        KTP.openRootScope().openSubScope(APP_SCOPE).installModules(AppModule(context = this), ModuleHolder())
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

const val APP_SCOPE = "APP_SCOPE"