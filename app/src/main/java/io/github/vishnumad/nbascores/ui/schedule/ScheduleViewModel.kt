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
import retrofit2.HttpException
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
        observableDate.value?.let { date ->
            observableDate.onNext(date.plusDays(1))
        }
    }

    fun prevDate() {
        observableDate.value?.let { date ->
            observableDate.onNext(date.minusDays(1))
        }
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
        logError(error)
        when (error) {
            is NoGamesException -> scheduleState.value = ScheduleViewState.Empty
            else -> {
                val errorMessage = errorMessageFor(error)
                scheduleState.value = ScheduleViewState.Failure(errorMessage)
            }
        }
    }

    private fun errorMessageFor(error: Throwable): String {
        when (error) {
            is HttpException -> {
                return buildString {
                    append("Error ${error.code()}")
                    if (error.message().isNotEmpty()) {
                        append(error.message())
                    }
                }
            }
            else -> {
                return "Could not load schedule for this date"
            }
        }
    }
}