package io.github.vishnumad.nbascores.utils

import androidx.recyclerview.widget.DiffUtil
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

typealias DiffFunction<T> = BiFunction<List<T>, List<T>, DiffUtil.Callback>

class RxDiffUtil {
    companion object {

        /**
         * An RxJava ObservableTransformer that is used to calculate DiffResults
         * for lists.
         *
         * Usage: observable.compose(RxDiffUtil.calculateDiff(BiFunction { old, new -> ... }))
         */
        @Suppress("UNCHECKED_CAST")
        fun <T> calculateDiff(diffFunction: DiffFunction<T>): ObservableTransformer<List<T>, Pair<List<T>, DiffUtil.DiffResult>> {

            val seed = Pair<List<T>, DiffUtil.DiffResult?>(emptyList(), null)

            return ObservableTransformer { upstream ->
                upstream
                    .scan(seed) { latestPair, newItems ->
                        val callback = diffFunction.apply(latestPair.first, newItems)
                        val result = DiffUtil.calculateDiff(callback, true)
                        return@scan Pair(newItems, result)
                    }
                    .skip(1) as Observable<Pair<List<T>, DiffUtil.DiffResult>> // Ignore seed pair and cast. We know DiffResult will never be null
            }
        }
    }

}