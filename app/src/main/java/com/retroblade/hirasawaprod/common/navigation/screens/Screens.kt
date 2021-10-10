package com.retroblade.hirasawaprod.common.navigation

import android.content.Context
import com.retroblade.hirasawaprod.common.navigation.screens.ActivityScreen
import com.retroblade.hirasawaprod.content.ui.ContentFragment
import com.retroblade.hirasawaprod.splash.ui.SplashFragment
import com.retroblade.hirasawaprod.viewer.ui.ViewerActivity

/**
 * Screens for navigator
 */

object SplashScreen : FragmentScreen {
    override fun getFragment() = SplashFragment.newInstance()
}

object ContentScreen : FragmentScreen {
    override fun getFragment() = ContentFragment.newInstance()
}

class ViewerScreen(private val context: Context, private val photoId: String) : ActivityScreen {
    override fun getIntent() = ViewerActivity.createIntent(context, photoId)
}
