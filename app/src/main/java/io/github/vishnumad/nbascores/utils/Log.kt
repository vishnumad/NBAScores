package io.github.vishnumad.nbascores.utils

import android.util.Log
import io.github.vishnumad.nbascores.BuildConfig


/**
 * Extension methods for easier logging
 * Usage: logDebug("Hello, world") inside any class
 */

fun Any.logDebug(message: String) {
    if (BuildConfig.DEBUG)
        Log.d(simpleName(), message)
}

fun Any.logInfo(message: String) {
    Log.i(simpleName(), message)
}

fun Any.logError(message: String, error: Throwable? = null) {
    Log.e(simpleName(), message, error)
}

fun Any.logError(error: Throwable) {
    Log.e(simpleName(), error.message, error)
}

fun Any.logVerbose(message: String) {
    Log.v(simpleName(), message)
}

fun Any.logWtf(message: String, error: Throwable? = null) {
    Log.wtf(simpleName(), message, error)
}

fun Any.logWtf(error: Throwable) {
    Log.wtf(simpleName(), error)
}

private fun Any.simpleName() = this::class.simpleName