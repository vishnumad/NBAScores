@file:Suppress("UNCHECKED_CAST")

package io.github.vishnumad.nbascores.di

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Extension functions to create ViewModels inside a [FragmentActivity] or [Fragment]
 */

inline fun <reified T : ViewModel> FragmentActivity.activityViewModel(crossinline provider: () -> T): T {
    return ViewModelProvider(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.activityViewModel(crossinline provider: () -> T): T {
    return ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModel(crossinline provider: () -> T): T {
    return ViewModelProvider(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
    }).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModelLazy(crossinline provider: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
        }
    }
}

inline fun <reified T : ViewModel> Fragment.activityViewModelLazy(crossinline provider: () -> T): Lazy<T> {
    return activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
        }
    }
}

inline fun <reified T : ViewModel> FragmentActivity.activityViewModelLazy(crossinline provider: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) = provider() as T
        }
    }
}
