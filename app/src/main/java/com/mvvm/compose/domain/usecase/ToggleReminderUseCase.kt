package com.mvvm.compose.domain.usecase

import com.mvvm.compose.domain.repository.ReminderRepository
import javax.inject.Inject

class ToggleReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(id: Int, isActive: Boolean) {
        repository.toggleReminderActive(id, isActive)
    }
}
