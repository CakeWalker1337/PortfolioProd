package com.retroblade.portfolioprod.common.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

/**
 * A class that holds a singleton navigator state, initialized with specific params
 */
class NavigatorHolder @Inject constructor() {

    private var navigator: Navigator? = null

    /**
     * Initializes navigator for [fragmentManager] and container with specific [containerId]
     * @return navigator state
     */
    fun initNavigator(@IdRes containerId: Int, fragmentManager: FragmentManager): Navigator {
        navigator = Navigator(containerId, fragmentManager)
        return navigator!!
    }

    /**
     * Returns current navigator state. Might be null
     */
    fun getNavigator(): Navigator? = navigator
}