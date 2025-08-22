@file:Suppress("FunctionName","unused")

package com.sapotos.ayarental.utils


import android.util.Log

// Logger for the project
class LogUtils {

    companion object {


        fun LogDebug(message: String?) {
            Log.d(TAG, message!!)
        }

        fun LogVerbose(message: String?) {
            Log.v(TAG, message!!)
        }

        fun LogError(TAG: String?, message: String?) {
            Log.e(TAG, message!!)
        }

        fun LogDebug(TAG: String?, message: String?) {
            Log.e(TAG, message!!)
        }

        fun LogVerbose(TAG: String?, message: String?) {
            Log.e(TAG, message!!)
        }

        private const val TAG = "LogUtils"
    }

}