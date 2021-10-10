package com.retroblade.hirasawaprod.common.navigation

import androidx.fragment.app.Fragment

/**
 * A navigation screen state interface that represents a method for receiving fragment instance
 */
fun interface FragmentScreen {
    fun getFragment(): Fragment
}
