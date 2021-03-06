package com.retroblade.portfolioprod.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.retroblade.portfolioprod.R
import kotlinx.android.synthetic.main.dialog_error_message.view.*
import moxy.MvpAppCompatFragment

/**
 * A base fragment class contains some base methods for each fragment
 */
abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    /**
     * Returns a layout resource for inflating the fragment
     */
    abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun showToastError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showAlertError(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_error_message, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        dialogView.tvMessage.text = message
        dialogView.btnOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}