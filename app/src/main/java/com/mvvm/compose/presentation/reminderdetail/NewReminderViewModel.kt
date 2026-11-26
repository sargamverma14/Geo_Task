package com.mvvm.compose.presentation.reminderdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mvvm.compose.domain.model.Reminder
import com.mvvm.compose.domain.repository.ReminderRepository
import com.mvvm.compose.domain.usecase.AddReminderUseCase
import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewReminderViewModel @Inject constructor(
    private val addReminderUseCase: AddReminderUseCase,
    private val reminderRepository: ReminderRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NewReminderState, NewReminderEvent, NewReminderEffect>(NewReminderState()) {

    private val reminderId: Int = savedStateHandle.get<String>("reminderId")?.toIntOrNull() ?: 0

    init {
        if (reminderId != 0) {
            loadReminder(reminderId)
        }
    }

    private fun loadReminder(id: Int) {
        viewModelScope.launch {
            reminderRepository.getReminderById(id)?.let { reminder ->
                setState {
                    copy(
                        id = reminder.id,
                        title = reminder.title,
                        note = reminder.note,
                        locationName = reminder.locationName,
                        radius = reminder.radius,
                        triggerType = if (reminder.triggerType == "On arrival") "Arrive" else "Leave",
                        repeat = reminder.repeat,
                        startTime = reminder.startTime,
                        endTime = reminder.endTime
                    )
                }
            }
        }
    }

    override fun onEvent(event: NewReminderEvent) {
        when (event) {
            is NewReminderEvent.UpdateTitle -> setState { copy(title = event.title) }
            is NewReminderEvent.UpdateNote -> setState { copy(note = event.note) }
            is NewReminderEvent.UpdateLocation -> setState { copy(locationName = event.location) }
            is NewReminderEvent.UpdateTrigger -> setState { copy(triggerType = event.trigger) }
            is NewReminderEvent.UpdateRepeat -> setState { copy(repeat = event.repeat) }
            is NewReminderEvent.UpdateStartTime -> setState { copy(startTime = event.time) }
            is NewReminderEvent.UpdateEndTime -> setState { copy(endTime = event.time) }
            is NewReminderEvent.ToggleDarkMode -> setState { copy(isDarkMode = !isDarkMode) }
            is NewReminderEvent.SaveReminder -> {
                viewModelScope.launch {
                    val state = uiState.value
                    val reminder = Reminder(
                        id = state.id,
                        title = state.title.ifBlank { "Untitled Reminder" },
                        note = state.note,
                        locationName = state.locationName,
                        radius = state.radius,
                        triggerType = if (state.triggerType == "Arrive") "On arrival" else "On leaving",
                        distance = "300 m",
                        repeat = state.repeat,
                        startTime = state.startTime,
                        endTime = state.endTime,
                        isActive = true
                    )
                    addReminderUseCase(reminder)
                    sendEffect(NewReminderEffect.NavigateBack)
                }
            }
            is NewReminderEvent.DeleteReminder -> {
                sendEffect(NewReminderEffect.NavigateBack)
            }
            is NewReminderEvent.NavigateBack -> {
                sendEffect(NewReminderEffect.NavigateBack)
            }
        }
    }
}
