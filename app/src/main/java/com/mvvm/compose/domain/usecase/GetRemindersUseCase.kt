package com.mvvm.compose.domain.usecase

import com.mvvm.compose.domain.model.Reminder
import com.mvvm.compose.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getReminders()
    }
}
