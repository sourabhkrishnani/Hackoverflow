package com.example.speakez.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_sessions")
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val userGoal: String,
    val question: String,
    val userTranscription: String,
    val aiFeedback: String,
    val wpm: Int,
    val fillerWordCount: Int,
)
