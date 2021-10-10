package com.retroblade.portfolioprod.common.navigation

import android.content.Context
import com.retroblade.portfolioprod.common.navigation.screens.ActivityScreen
import com.retroblade.portfolioprod.feature.content.ui.ContentFragment
import com.retroblade.portfolioprod.feature.splash.ui.SplashFragment
import com.retroblade.portfolioprod.feature.viewer.ui.ViewerActivity

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
