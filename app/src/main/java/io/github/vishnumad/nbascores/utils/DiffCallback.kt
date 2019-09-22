package io.github.vishnumad.nbascores.utils

import androidx.recyclerview.widget.DiffUtil

abstract class DiffCallback<T>(
        private val oldItems: List<T>,
        private val newItems: List<T>
) : DiffUtil.Callback() {

    abstract fun areItemsSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsSame(oldItem: T, newItem: T): Boolean
    abstract fun getChangePayload(oldItem: T, newItem: T): Any?


    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return areItemsSame(oldItems[oldPos], newItems[newPos])
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return areContentsSame(oldItems[oldPos], newItems[newPos])
    }

    override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
        return getChangePayload(oldItems[oldPos], newItems[newPos])
    }
}