package io.github.vishnumad.nbascores.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.vishnumad.nbascores.data.exceptions.NoGamesException
import io.github.vishnumad.nbascores.data.models.ScheduledGame
import io.github.vishnumad.nbascores.data.repositories.ScheduleRepository
import io.github.vishnumad.nbascores.di.DATE_ET
import io.github.vishnumad.nbascores.utils.DateTimeHelper
import io.github.vishnumad.nbascores.utils.logError
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Named

class ScheduleViewModel @Inject constructor(
    private val scheduleRepo: ScheduleRepository,
    private val dateTimeHelper: DateTimeHelper,
    @Named(DATE_ET) private val startDate: LocalDate
) : ViewModel() {

    private val scheduleState = MutableLiveData<ScheduleViewState>()
    private val dateSwitcherState = MutableLiveData<DateSwitcherState>()

    private val observableDate: BehaviorSubject<LocalDate> =
        BehaviorSubject.createDefault(startDate)

    private val disposables = CompositeDisposable()

    init {
        // Change date switcher label when date changes
        disposables += observableDate.subscribe { date ->
            val label = dateTimeHelper.humanReadableDate(date)
            dateSwitcherState.value =
                DateSwitcherState(label, date.year, date.monthValue, date.dayOfMonth)
        }

        // Get games when date changes
        disposables += observableDate
            .switchMap<List<ScheduledGame>> { scheduleRepo.getScheduledGames(it) }
            .doOnNext { games ->
                if (games.isEmpty()) {
                    fetchSchedule()
                    scheduleState.value = ScheduleViewState.Loading
                }
            }
            .filter { it.isNotEmpty() }
            .subscribe { schedule ->
                scheduleState.value = ScheduleViewState.Success(schedule)
            }
    }

    fun getScheduleState(): LiveData<ScheduleViewState> = scheduleState

    fun getDateSwitcherState(): LiveData<DateSwitcherState> = dateSwitcherState

    fun nextDate() {
        observableDate.onNext(observableDate.value!!.plusDays(1))
    }

    fun prevDate() {
        observableDate.onNext(observableDate.value!!.minusDays(1))
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month, day)
        observableDate.onNext(date)
    }

    fun resetDate() {
        observableDate.onNext(startDate)
    }

    fun onSwipeRefresh() {
        fetchSchedule()
    }

    fun onReloadButtonClick() {
        scheduleState.value = ScheduleViewState.Loading
        fetchSchedule()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun fetchSchedule() {
        disposables += scheduleRepo.fetchSchedule(observableDate.value!!)
            .subscribeBy(onError = { handleError(it) })
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is NoGamesException -> scheduleState.value = ScheduleViewState.Empty
            else -> {
                logError(error)
                error.printStackTrace()
                scheduleState.value =
                    ScheduleViewState.Failure("Could not load schedule for this date. Try again later!")
            }
        }
    }
}