package io.github.vishnumad.nbascores.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Converts dp value to pixel value
 */
fun Context.convertDpToPx(dp: Float) =
    dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

/**
 * Converts pixel value to dp value
 */
fun Context.convertPxToDp(px: Float) =
    px / (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
