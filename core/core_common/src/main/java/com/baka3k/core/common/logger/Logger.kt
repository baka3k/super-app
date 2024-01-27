package com.baka3k.core.common.logger

import android.util.Log
//import com.baka3k.core.common.BuildConfig

object Logger {
    private const val TAG = "HiApplication"

    fun d(message: String, throwable: Throwable? = null) {
//        if (BuildConfig.DEBUG) {
//
//        }
        Log.d(TAG, message, throwable)
    }

    fun w(message: String, throwable: Throwable? = null) {
        Log.w(TAG, message, throwable)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
    }

    fun d(tag: String, message: String, throwable: Throwable? = null) {
//        if (BuildConfig.DEBUG) {
//            Log.d(tag, message, throwable)
//        }
        Log.d(tag, message, throwable)
    }

    fun w(tag: String, message: String, throwable: Throwable? = null) {
        Log.w(tag, message, throwable)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }

    const val VERBOSE = 2
    const val DEBUG = 3
    const val INFO = 4
    const val WARN = 5
    const val ERROR = 6
    const val ASSERT = 7
}