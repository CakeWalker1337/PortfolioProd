package com.retroblade.hirasawaprod.base

import android.app.AlertDialog
import android.widget.Toast
import com.retroblade.hirasawaprod.R
import kotlinx.android.synthetic.main.dialog_error_message.view.*
import moxy.MvpAppCompatActivity

/**
 * An activity base class contains base methods for each activity
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun showToastError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showAlertError(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_error_message, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).create()
        dialogView.tvMessage.text = message
        dialogView.btnOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}