package com.mvvm.compose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvvm.compose.domain.model.Reminder

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val locationName: String,
    val radius: String,
    val triggerType: String,
    val distance: String,
    val repeat: Boolean,
    val startTime: String,
    val endTime: String,
    val isActive: Boolean,
    val isDone: Boolean
)

fun ReminderEntity.toDomain(): Reminder {
    return Reminder(
        id = id,
        title = title,
        note = note,
        locationName = locationName,
        radius = radius,
        triggerType = triggerType,
        distance = distance,
        repeat = repeat,
        startTime = startTime,
        endTime = endTime,
        isActive = isActive,
        isDone = isDone
    )
}

fun Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        note = note,
        locationName = locationName,
        radius = radius,
        triggerType = triggerType,
        distance = distance,
        repeat = repeat,
        startTime = startTime,
        endTime = endTime,
        isActive = isActive,
        isDone = isDone
    )
}
