package com.example.speakez.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.speakez.data.model.PracticeSession
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: PracticeSession): Long

    @Query("SELECT * FROM practice_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PracticeSession>>

    @Query("SELECT * FROM practice_sessions WHERE id = :id")
    suspend fun getSessionById(id: Int): PracticeSession?
}
