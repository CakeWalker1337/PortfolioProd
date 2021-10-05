package com.retroblade.hirasawaprod.common.navigation

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.retroblade.hirasawaprod.common.navigation.screens.ActivityScreen

/**
 * @author m.a.kovalev
 */
class Navigator(
    @IdRes private var container: Int,
    private var fragmentManager: FragmentManager
) {

    fun executeNavigation(screen: FragmentScreen) {
        fragmentManager.beginTransaction().replace(container, screen.getFragment()).commit()
    }

    fun executeNavigation(context: Context, screen: ActivityScreen) {
        context.startActivity(screen.getIntent())
    }
}