package com.mvvm.compose.di

import android.content.Context
import androidx.room.Room
import com.mvvm.compose.data.local.ReminderDao
import com.mvvm.compose.data.local.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext context: Context): ReminderDatabase {
        return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            ReminderDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(database: ReminderDatabase): ReminderDao {
        return database.reminderDao
    }
}
