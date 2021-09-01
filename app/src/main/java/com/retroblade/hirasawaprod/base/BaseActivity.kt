package com.retroblade.hirasawaprod.base

import android.os.Bundle
import com.retroblade.hirasawaprod.APP_SCOPE
import com.retroblade.hirasawaprod.common.di.ModuleHolder
import moxy.MvpAppCompatActivity
import toothpick.Scope
import toothpick.ktp.KTP

/**
 * @author m.a.kovalev
 */
abstract class BaseActivity : MvpAppCompatActivity() {

    lateinit var scope: Scope
    lateinit var fragmentScopeName: String
    protected lateinit var moduleHolder: ModuleHolder

    protected open val parentScope: String = APP_SCOPE

    open fun installModules(scope: Scope) {}

    private fun getObjectScope() = "${javaClass.simpleName}_${hashCode()}"

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(EXTRA_ACTIVITY_SCOPE_NAME) ?: getObjectScope()
        if (KTP.isScopeOpen(fragmentScopeName)) {
            scope = KTP.openScope(fragmentScopeName)
        } else {
            scope = KTP.openScopes(parentScope, fragmentScopeName)
            moduleHolder = scope.getInstance(ModuleHolder::class.java)
            installModules(scope)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_ACTIVITY_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        KTP.closeScope(fragmentScopeName)
        super.onDestroy()
    }

    private companion object {
        const val EXTRA_ACTIVITY_SCOPE_NAME = "EXTRA_ACTIVITY_SCOPE_NAME"
    }
}