package com.retroblade.hirasawaprod

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.retroblade.hirasawaprod.content.ui.ContentFragment
import com.retroblade.hirasawaprod.splash.SplashFragment
import com.retroblade.hirasawaprod.viewer.ViewerFragment

/**
 * @author m.a.kovalev
 */
object Screens {
    fun Splash() = FragmentScreen { SplashFragment.newInstance() }
    fun Content() = FragmentScreen { ContentFragment.newInstance() }
    fun Viewer(photoId: String) = FragmentScreen { ViewerFragment.newInstance(photoId) }
}