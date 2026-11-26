package com.mvvm.compose.presentation.reminderdetail

import com.mvvm.compose.domain.model.Reminder

data class NewReminderState(
    val id: Int = 0,
    val title: String = "",
    val note: String = "",
    val locationName: String = "City Market",
    val radius: String = "300 m radius",
    val triggerType: String = "Arrive", // "Arrive" or "Leave"
    val repeat: Boolean = true,
    val startTime: String = "08:00",
    val endTime: String = "20:00",
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false
)

sealed class NewReminderEvent {
    data class UpdateTitle(val title: String) : NewReminderEvent()
    data class UpdateNote(val note: String) : NewReminderEvent()
    data class UpdateLocation(val location: String) : NewReminderEvent()
    data class UpdateTrigger(val trigger: String) : NewReminderEvent()
    data class UpdateRepeat(val repeat: Boolean) : NewReminderEvent()
    data class UpdateStartTime(val time: String) : NewReminderEvent()
    data class UpdateEndTime(val time: String) : NewReminderEvent()
    object ToggleDarkMode : NewReminderEvent()
    object SaveReminder : NewReminderEvent()
    object DeleteReminder : NewReminderEvent()
    object NavigateBack : NewReminderEvent()
}

sealed class NewReminderEffect {
    object NavigateBack : NewReminderEffect()
    data class ShowSnackbar(val message: String) : NewReminderEffect()
}
