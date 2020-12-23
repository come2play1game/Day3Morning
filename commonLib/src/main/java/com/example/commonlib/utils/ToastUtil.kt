package com.example.commonlib.utils

import android.widget.Toast
import com.example.commonlib.utils.CommonApplication.commonApp

object ToastUtil {
    fun showShort(msg: String?) {
        Toast.makeText(commonApp, msg, Toast.LENGTH_SHORT).show()
    }
}