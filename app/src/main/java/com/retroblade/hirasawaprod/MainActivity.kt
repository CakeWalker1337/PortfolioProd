package com.retroblade.hirasawaprod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class MainActivity : AppCompatActivity() {

    private val router: Router by inject<Router>()
    private val navigatorHolder: NavigatorHolder by inject<NavigatorHolder>()

    private val navigator = AppNavigator(this, R.id.container, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KTP.openScope(APP_SCOPE).inject(this)
        navigatorHolder.setNavigator(navigator)
        router.newRootScreen(Screens.Splash())
    }

}