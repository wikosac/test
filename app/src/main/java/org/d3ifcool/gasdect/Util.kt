package org.d3ifcool.gasdect

import android.content.Context
import android.widget.Toast

object Util {

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

}