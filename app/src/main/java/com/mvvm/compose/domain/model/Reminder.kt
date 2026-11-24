package com.mvvm.compose.domain.model

data class Reminder(
    val id: Int = 0,
    val title: String,
    val note: String = "",
    val locationName: String,
    val radius: String = "300 m radius",
    val triggerType: String, // "Arrive" or "Leave"
    val distance: String = "300 m",
    val repeat: Boolean = true,
    val startTime: String = "08:00",
    val endTime: String = "20:00",
    val isActive: Boolean = true,
    val isDone: Boolean = false
)
