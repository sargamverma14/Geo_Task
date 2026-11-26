package com.mvvm.compose.domain.repository

import com.mvvm.compose.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getReminders(): Flow<List<Reminder>>
    suspend fun getReminderById(id: Int): Reminder?
    suspend fun insertReminder(reminder: Reminder)
    suspend fun toggleReminderActive(id: Int, isActive: Boolean)
    suspend fun deleteReminder(id: Int)
}
