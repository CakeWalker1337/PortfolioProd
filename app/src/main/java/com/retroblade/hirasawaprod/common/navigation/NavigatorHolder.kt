package com.retroblade.hirasawaprod.common.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

/**
 * @author m.a.kovalev
 */
class NavigatorHolder @Inject constructor() {

    private var navigator: Navigator? = null

    fun initNavigator(@IdRes containerId: Int, fragmentManager: FragmentManager): Navigator {
        navigator = Navigator(containerId, fragmentManager)
        return navigator!!
    }

    fun getNavigator(): Navigator? = navigator
}