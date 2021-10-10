package com.retroblade.hirasawaprod.common.navigation

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.retroblade.hirasawaprod.common.navigation.screens.ActivityScreen

/**
 * A navigator class that executes navigation actions to fragments and activities
 */
class Navigator(
    @IdRes private var container: Int,
    private var fragmentManager: FragmentManager
) {

    /**
     * Executes navigation to fragment [screen]
     */
    fun executeNavigation(screen: FragmentScreen) {
        fragmentManager.beginTransaction().replace(container, screen.getFragment()).commit()
    }

    /**
     * Executes navigation to activity [screen] using in bounds of [context]
     */
    fun executeNavigation(context: Context, screen: ActivityScreen) {
        context.startActivity(screen.getIntent())
    }
}