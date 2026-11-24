package com.mvvm.compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvvm.compose.data.local.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ReminderDatabase : RoomDatabase() {
    abstract val reminderDao: ReminderDao

    companion object {
        const val DATABASE_NAME = "reminders_db"
    }
}
