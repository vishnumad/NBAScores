package io.github.vishnumad.nbascores.utils

class Utils {
    companion object {

        fun percentage(numerator: Int, denominator: Int): String {
            val pct = numerator.toDouble() / denominator
            return String.format("%.3f", pct)
        }
    }
}