package com.example.speakez.domain.model

import java.util.Date

data class Session(
    val id: Int,
    val date: Date,
    val userGoal: UserGoal,
    val analysisResult: AnalysisResult
)
