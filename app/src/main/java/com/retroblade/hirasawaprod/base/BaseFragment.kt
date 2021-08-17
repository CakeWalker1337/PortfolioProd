package com.retroblade.hirasawaprod.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.retroblade.hirasawaprod.R
import kotlinx.android.synthetic.main.dialog_error_message.view.*
import moxy.MvpAppCompatFragment

/**
 * @author m.a.kovalev
 */
abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract fun getLayoutRes(): Int

    private fun findNavController() = (this as Fragment).findNavController()

    override fun navigateTo(@IdRes action: Int) {
        findNavController().navigate(action)
    }

    override fun navigateTo(@IdRes action: Int, data: Bundle) {
        findNavController().navigate(action, data)
    }

    open fun navigateBack() {
        findNavController().popBackStack()
    }

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }
}