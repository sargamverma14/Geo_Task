package com.mvvm.compose.presentation.reminders

import com.mvvm.compose.domain.model.Reminder

enum class ReminderFilter {
    ALL, ACTIVE, DONE
}

data class RemindersState(
    val reminders: List<Reminder> = emptyList(),
    val filter: ReminderFilter = ReminderFilter.ALL,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddDialog: Boolean = false
)

sealed class RemindersEvent {
    data class ChangeFilter(val filter: ReminderFilter) : RemindersEvent()
    data class ToggleReminder(val id: Int, val isActive: Boolean) : RemindersEvent()
    data class AddReminder(val reminder: Reminder) : RemindersEvent()
    data class DeleteReminder(val id: Int) : RemindersEvent()
    object ToggleAddDialog : RemindersEvent()
    object NavigateToMap : RemindersEvent()
    object NavigateToSettings : RemindersEvent()
}

sealed class RemindersEffect {
    data class ShowSnackbar(val message: String) : RemindersEffect()
    object NavigateToMap : RemindersEffect()
    object NavigateToSettings : RemindersEffect()
}
