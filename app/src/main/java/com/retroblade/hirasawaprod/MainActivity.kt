package com.retroblade.hirasawaprod

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import com.retroblade.hirasawaprod.base.BaseActivity
import com.retroblade.hirasawaprod.common.navigation.NavigatorHolder
import com.retroblade.hirasawaprod.common.navigation.SplashScreen
import toothpick.ktp.delegate.inject

class MainActivity : BaseActivity() {

    private var prevBackPressedTime: Long = 0L

    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
        setContentView(R.layout.activity_main)
        val navigator = navigatorHolder.initNavigator(R.id.container, supportFragmentManager)
        navigator.executeNavigation(SplashScreen)
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            window.navigationBarColor = getColor(R.color.content_progress_background_color)
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime < prevBackPressedTime + 2000L) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            prevBackPressedTime = currentTime
        }
    }

}