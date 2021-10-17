package com.bazar.bane.bazarshahr.util

import android.widget.Toast
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext

object ToastUtil {

    private var mToast: Toast? = null

    fun showToast(message: String?) {
        if (mToast == null) {
            mToast = Toast.makeText(applicationContext(), message, Toast.LENGTH_SHORT)
        } else {
            mToast!!.cancel()
            mToast = Toast.makeText(applicationContext(), message, Toast.LENGTH_SHORT)
            mToast!!.duration = Toast.LENGTH_SHORT
        }
        mToast!!.show()

    }

    fun showToast(message: Int?) {
        val errorMessage= applicationContext().getString(message!!)
        if (mToast == null) {
            mToast = Toast.makeText(applicationContext(), errorMessage, Toast.LENGTH_SHORT)
        } else {
            mToast!!.cancel()
            mToast = Toast.makeText(applicationContext(), errorMessage, Toast.LENGTH_SHORT)
            mToast!!.duration = Toast.LENGTH_SHORT
        }
        mToast!!.show()

    }
}