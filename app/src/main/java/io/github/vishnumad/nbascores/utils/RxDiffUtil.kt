package io.github.vishnumad.nbascores.utils

import androidx.recyclerview.widget.DiffUtil
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class RxDiffUtil {
    companion object {

        /**
         * An RxJava ObservableTransformer that is used to calculate DiffResults
         * for lists.
         *
         * Usage: observable.compose(RxDiffUtil.calculateDiff(BiFunction { old, new -> ... }))
         */
        fun <T> calculateDiff(
                differ: BiFunction<List<T>, List<T>, DiffUtil.Callback>
        ): ObservableTransformer<List<T>, Pair<List<T>, DiffUtil.DiffResult>> {

            val seed = Pair<List<T>, DiffUtil.DiffResult?>(emptyList(), null)

            return ObservableTransformer { upstream ->
                upstream
                    .scan(seed) { latestPair, newItems ->
                        val callback = differ.apply(latestPair.first, newItems)
                        val result = DiffUtil.calculateDiff(callback, true)
                        return@scan Pair(newItems, result)
                    }
                    .skip(1) // Ignore the seed pair
                    .map { (list, diff) -> Pair(list, diff!!) } // We know the diff will never be null
            }
        }
    }

}