package com.retroblade.portfolioprod

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import com.retroblade.portfolioprod.base.BaseActivity
import com.retroblade.portfolioprod.common.navigation.NavigatorHolder
import com.retroblade.portfolioprod.common.navigation.SplashScreen
import javax.inject.Inject

/**
 * Main activity holds a container for switching between two fragments:
 * Splash screen fragment and Content fragment.
 */
class MainActivity : BaseActivity() {

    // Buffer time for double click exit
    private var prevBackPressedTime: Long = 0L

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        // component injector must be called before onCreate
        (applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // switching fragment container to splash screen state
        val navigator = navigatorHolder.initNavigator(R.id.container, supportFragmentManager)
        navigator.executeNavigation(SplashScreen)

        // changing navigation bar color for night mode
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            window.navigationBarColor = getColor(R.color.content_progress_background_color)
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime < prevBackPressedTime + EXIT_DOUBLE_TAP_BUFFER_MILLIS) {
            finishAffinity()
        } else {
            Toast.makeText(this, getString(R.string.content_double_click_exit_message), Toast.LENGTH_SHORT).show()
            prevBackPressedTime = currentTime
        }
    }

    private companion object {
        const val EXIT_DOUBLE_TAP_BUFFER_MILLIS = 2000L
    }
}