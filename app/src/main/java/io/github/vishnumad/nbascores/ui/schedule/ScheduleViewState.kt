package io.github.vishnumad.nbascores.ui.schedule

import io.github.vishnumad.nbascores.data.models.ScheduledGame

sealed class ScheduleViewState {
    object Loading : ScheduleViewState()
    object Empty : ScheduleViewState()
    class Success(val schedule: List<ScheduledGame>) : ScheduleViewState()
    class Failure(val message: String) : ScheduleViewState()
}