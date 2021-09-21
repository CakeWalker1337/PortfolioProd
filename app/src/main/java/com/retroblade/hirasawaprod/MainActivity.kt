package com.retroblade.hirasawaprod

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.retroblade.hirasawaprod.base.BaseActivity
import toothpick.ktp.delegate.inject

class MainActivity : BaseActivity() {

    private val router: Router by inject<Router>()
    private val navigatorHolder: NavigatorHolder by inject<NavigatorHolder>()

    private val navigator = AppNavigator(this, R.id.container, supportFragmentManager)

    private var prevBackPressedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
        setContentView(R.layout.activity_main)
        navigatorHolder.setNavigator(navigator)
        router.newRootScreen(Screens.Splash())
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