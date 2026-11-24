package com.mvvm.compose.presentation.reminders

import androidx.lifecycle.viewModelScope
import com.mvvm.compose.domain.model.Reminder
import com.mvvm.compose.domain.usecase.AddReminderUseCase
import com.mvvm.compose.domain.usecase.GetRemindersUseCase
import com.mvvm.compose.domain.usecase.ToggleReminderUseCase
import com.mvvm.compose.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val toggleReminderUseCase: ToggleReminderUseCase,
    private val addReminderUseCase: AddReminderUseCase
) : BaseViewModel<RemindersState, RemindersEvent, RemindersEffect>(RemindersState()) {

    init {
        observeReminders()
    }

    override fun onEvent(event: RemindersEvent) {
        when (event) {
            is RemindersEvent.ChangeFilter -> {
                setState { copy(filter = event.filter) }
            }
            is RemindersEvent.ToggleReminder -> {
                viewModelScope.launch {
                    toggleReminderUseCase(event.id, event.isActive)
                }
            }
            is RemindersEvent.AddReminder -> {
                viewModelScope.launch {
                    addReminderUseCase(event.reminder)
                    setState { copy(showAddDialog = false) }
                }
            }
            is RemindersEvent.DeleteReminder -> {
                // optional delete
            }
            is RemindersEvent.ToggleAddDialog -> {
                setState { copy(showAddDialog = !uiState.value.showAddDialog) }
            }
            is RemindersEvent.NavigateToMap -> {
                sendEffect(RemindersEffect.NavigateToMap)
            }
            is RemindersEvent.NavigateToSettings -> {
                sendEffect(RemindersEffect.NavigateToSettings)
            }
        }
    }

    private fun observeReminders() {
        setState { copy(isLoading = true) }
        getRemindersUseCase().onEach { reminders ->
            val list = if (reminders.isEmpty()) {
                val defaults = listOf(
                    Reminder(id = 1, title = "Buy milk", locationName = "City Market", triggerType = "On arrival", distance = "300 m", isActive = true),
                    Reminder(id = 2, title = "Drop off dry cleaning", locationName = "Cedar Cleaners", triggerType = "On leaving", distance = "150 m", isActive = true),
                    Reminder(id = 3, title = "Pick up parcel", locationName = "Oakridge Post", triggerType = "On arrival", distance = "200 m", isActive = true),
                    Reminder(id = 4, title = "Water balcony plants", locationName = "Home", triggerType = "On arrival", distance = "100 m", isActive = false)
                )
                viewModelScope.launch {
                    defaults.forEach { addReminderUseCase(it) }
                }
                defaults
            } else {
                reminders
            }

            setState { copy(reminders = list, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
