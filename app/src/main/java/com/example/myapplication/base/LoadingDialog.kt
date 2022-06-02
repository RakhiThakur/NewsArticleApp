package com.example.myapplication.base

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import timber.log.Timber

class LoadingDialog {
    companion object {
        private var dialog: AlertDialog? = null
    }

    fun buildDialog(context: Context) {
        if (dialog == null) {
            val builder = AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_loading, null))
            builder.setCancelable(true)
            dialog = builder.create()
        }
    }

    fun showProgress(context: Context) {
        if (dialog?.isShowing?.not() == true) {
            try {
                dialog?.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
                Timber.e("Activity token is bad")
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    fun dismissDialog() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }
}