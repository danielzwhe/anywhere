package im.danielzwhe.app.anywhere.util

import android.util.Log
import im.danielzwhe.app.anywhere.Config

object Logger {

    const val DEFAULT_TAG = "anywhere"
    const val LEVEL_VERBOSE = 5
    const val LEVEL_DEBUG = 4
    const val LEVEL_INFO = 3
    const val LEVEL_WARN = 2
    const val LEVEL_ERROR = 1
    const val LEVEL_NONE = 0

    fun v(message: String) {
        v(DEFAULT_TAG, message)
    }

    fun v(tag: String, message: String) {
        if (Config.debugLevel >= LEVEL_VERBOSE) {
            Log.v(tag, message)
        }
    }

    fun d(message: String) {
        d(DEFAULT_TAG, message)
    }

    fun d(tag: String, message: String) {
        if (Config.debugLevel >= LEVEL_DEBUG) {
            Log.d(tag, message)
        }
    }

    fun i(message: String) {
        i(DEFAULT_TAG, message)
    }

    fun i(tag: String, message: String) {
        if (Config.debugLevel >= LEVEL_INFO) {
            Log.i(tag, message)
        }
    }

    fun w(message: String) {
        w(DEFAULT_TAG, message)
    }

    fun w(tag: String, message: String) {
        if (Config.debugLevel >= LEVEL_WARN) {
            Log.w(tag, message)
        }
    }

    fun e(message: String) {
        e(DEFAULT_TAG, message)
    }

    fun e(tag: String, message: String) {
        if (Config.debugLevel >= LEVEL_ERROR) {
            Log.e(tag, message)
        }
    }
}