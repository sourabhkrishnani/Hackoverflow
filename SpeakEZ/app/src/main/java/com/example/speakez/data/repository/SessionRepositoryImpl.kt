package com.example.speakez.data.repository

import com.example.speakez.data.data_source.SessionDao
import com.example.speakez.data.model.PracticeSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(private val sessionDao: SessionDao) {
    val allSessions: Flow<List<PracticeSession>> = sessionDao.getAllSessions()

    suspend fun insertSession(session: PracticeSession): Long {
        return sessionDao.insertSession(session)
    }

    suspend fun getSessionById(id: Int): PracticeSession? {
        return sessionDao.getSessionById(id)
    }
}
