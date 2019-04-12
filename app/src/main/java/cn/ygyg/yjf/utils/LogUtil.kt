package cn.ygyg.yjf.utils

import android.util.Log

object LogUtil {
    val VERBOSE = 2
    val DEBUG = 3
    val INFO = 4
    val WARN = 5
    val ERROR = 6
    val ASSERT = 7
    var level = VERBOSE
    fun v(msg: String, tag: String) {
        if (level <= VERBOSE) Log.v(tag, msg)
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        if (level <= VERBOSE) Log.v(tag, msg, tr)
    }

    fun d(msg: String, tag: String) {
        if (level <= DEBUG) Log.d(tag, msg)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        if (level <= DEBUG) Log.d(tag, msg, tr)
    }

    fun i(tag: String, msg: String) {
        if (level <= INFO) Log.i(tag, msg)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        if (level <= INFO) Log.i(tag, msg, tr)
    }

    fun w(tag: String, msg: String) {
        if (level <= WARN) Log.w(tag, msg)
    }

    fun w(tag: String, tr: Throwable) {
        if (level <= WARN) Log.w(tag, tr)
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        if (level <= WARN) Log.w(tag, msg, tr)
    }

    fun e(tag: String, msg: String) {
        if (level <= ERROR) Log.e(tag, msg)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (level <= ERROR) Log.e(tag, msg, tr)
    }

}