package com.retroblade.hirasawaprod

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.retroblade.hirasawaprod.content.ui.ContentFragment
import com.retroblade.hirasawaprod.splash.ui.SplashFragment

/**
 * @author m.a.kovalev
 */
object Screens {
    fun Splash() = FragmentScreen { SplashFragment.newInstance() }
    fun Content() = FragmentScreen { ContentFragment.newInstance() }
}