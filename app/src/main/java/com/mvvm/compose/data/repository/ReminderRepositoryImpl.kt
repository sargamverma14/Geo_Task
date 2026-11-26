package com.mvvm.compose.data.repository

import com.mvvm.compose.data.local.ReminderDao
import com.mvvm.compose.data.local.entity.toDomain
import com.mvvm.compose.data.local.entity.toEntity
import com.mvvm.compose.domain.model.Reminder
import com.mvvm.compose.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {

    override fun getReminders(): Flow<List<Reminder>> {
        return dao.getReminders().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getReminderById(id: Int): Reminder? {
        return dao.getReminderById(id)?.toDomain()
    }

    override suspend fun insertReminder(reminder: Reminder) {
        dao.insertReminder(reminder.toEntity())
    }

    override suspend fun toggleReminderActive(id: Int, isActive: Boolean) {
        dao.toggleReminderActive(id, isActive)
    }

    override suspend fun deleteReminder(id: Int) {
        dao.deleteReminder(id)
    }
}
