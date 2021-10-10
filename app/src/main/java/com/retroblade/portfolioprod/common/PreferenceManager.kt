package com.retroblade.portfolioprod.common

import android.content.Context
import android.content.SharedPreferences
import com.retroblade.portfolioprod.BuildConfig
import javax.inject.Inject

/**
 * A manager provides some methods for work with shared prefs
 */
class PreferenceManager @Inject constructor(
    private val context: Context
) {

    // prefs instance
    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(BuildConfig.PREFS_NAME, Context.MODE_PRIVATE) }

    /**
     * Checks if splash screen has ever been shown
     */
    fun wasSplashShown(): Boolean {
        return prefs.getBoolean(PREF_SPLASH_SHOWN, false)
    }

    /**
     * Sets the pref flag was splash screen [shown] or not
     */
    fun setSplashShown(shown: Boolean) {
        prefs.edit().putBoolean(PREF_SPLASH_SHOWN, shown).apply()
    }

    private companion object {
        const val PREF_SPLASH_SHOWN = "pref_splash_shown"
    }
}