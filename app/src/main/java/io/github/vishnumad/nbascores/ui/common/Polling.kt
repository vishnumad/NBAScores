package io.github.vishnumad.nbascores.ui.common

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class Polling(
    private val intervalMs: Long,
    private val initialDelay: Long = 0,
    private val task: Completable,
    private val observeScheduler: Scheduler,
    private val onPollError: (Throwable) -> Unit
) {

    private var enabled: Boolean = true
    private var lastUpdate: Long = 0
    private var pollDisposable: Disposable? = null

    fun start() {
        // Only start polling if enabled and isn't already polling
        if (!enabled || pollDisposable != null) return

        pollDisposable = Observable
            .interval(calculateDelay(), intervalMs, TimeUnit.MILLISECONDS)
            .takeUntil { !enabled } // stop polling when disabled
            .flatMapCompletable {
                task.doOnComplete {
                    // Update the last updated time
                    lastUpdate = System.currentTimeMillis()
                }
            }
            .observeOn(observeScheduler)
            .subscribeBy(onError = { onPollError(it) })
    }

    fun stop() {
        pollDisposable?.dispose()
        pollDisposable = null
    }

    fun enable() {
        this.enabled = true
        if (pollDisposable == null)
            start()
    }

    fun disable() {
        this.enabled = false
        stop()
    }

    fun isPolling() = pollDisposable != null && !pollDisposable!!.isDisposed

    private fun calculateDelay(): Long {
        val delta = System.currentTimeMillis() - lastUpdate
        val delay = if (delta in 0..intervalMs) intervalMs - delta else 0
        if (lastUpdate == 0L) {
            // First run; add initial delay
            return delay + initialDelay
        } else {
            return delay
        }
    }

}