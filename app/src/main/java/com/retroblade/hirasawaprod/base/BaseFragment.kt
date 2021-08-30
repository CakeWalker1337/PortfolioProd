package com.retroblade.hirasawaprod.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.retroblade.hirasawaprod.APP_SCOPE
import com.retroblade.hirasawaprod.R
import com.retroblade.hirasawaprod.common.di.ModuleHolder
import kotlinx.android.synthetic.main.dialog_error_message.view.*
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.ktp.KTP

/**
 * @author m.a.kovalev
 */
abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    lateinit var scope: Scope
    lateinit var fragmentScopeName: String
    protected lateinit var moduleHolder: ModuleHolder

    protected open val parentScope: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName ?: APP_SCOPE
    }

    open fun installModules(scope: Scope) {}

    private fun getObjectScope() = "${javaClass.simpleName}_${hashCode()}"

    abstract fun getLayoutRes(): Int


    override fun showToastError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showAlertError(message: String) {
        val v = layoutInflater.inflate(R.layout.dialog_error_message, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(v).create()
        v.tvMessage.text = message
        v.btnOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(EXTRA_FRAGMENT_SCOPE_NAME) ?: getObjectScope()
        if (KTP.isScopeOpen(fragmentScopeName)) {
            scope = KTP.openScope(fragmentScopeName)
        } else {
            scope = KTP.openScopes(parentScope, fragmentScopeName)
            moduleHolder = scope.getInstance(ModuleHolder::class.java)
            installModules(scope)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        KTP.closeScope(fragmentScopeName)
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }
}

private const val EXTRA_FRAGMENT_SCOPE_NAME = "EXTRA_FRAGMENT_SCOPE_NAME"
