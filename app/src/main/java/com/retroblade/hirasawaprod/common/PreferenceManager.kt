package com.retroblade.hirasawaprod.common

import android.content.Context
import android.content.SharedPreferences
import com.retroblade.hirasawaprod.BuildConfig
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class PreferenceManager @Inject constructor(
    private val context: Context
) {

    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(BuildConfig.PREFS_NAME, Context.MODE_PRIVATE) }

    fun wasSplashShown(): Boolean {
        return prefs.getBoolean(PREF_SPLASH_SHOWN, false)
    }

    fun setSplashShown(shown: Boolean) {
        prefs.edit().putBoolean(PREF_SPLASH_SHOWN, shown).apply()
    }

    private companion object {
        const val PREF_SPLASH_SHOWN = "pref_splash_shown"
    }
}