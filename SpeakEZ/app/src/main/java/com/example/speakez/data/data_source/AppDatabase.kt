package com.example.speakez.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.speakez.data.model.PracticeSession

@Database(entities = [PracticeSession::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
}
