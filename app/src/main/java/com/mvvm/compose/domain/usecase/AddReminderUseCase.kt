package com.mvvm.compose.domain.usecase

import com.mvvm.compose.domain.model.Reminder
import com.mvvm.compose.domain.repository.ReminderRepository
import javax.inject.Inject

class AddReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.insertReminder(reminder)
    }
}
