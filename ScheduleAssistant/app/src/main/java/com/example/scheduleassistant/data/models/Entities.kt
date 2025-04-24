package com.example.scheduleassistant.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String,
    val details: String?,
    val frequency: String,
    val categoryId: Int?,
    val createdAt: Date = Date()
)

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String,
    val details: String?,
    val frequency: String,
    val categoryId: Int?,
    val createdAt: Date = Date()
)

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val amount: Double,
    val description: String?,
    val categoryId: Int?,
    val date: Date = Date()
)

@Entity
data class Earning(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val amount: Double,
    val description: String?,
    val categoryId: Int?,
    val date: Date = Date()
)

@Entity
data class WaterIntake(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val amountMl: Int,
    val date: Date = Date()
)

@Entity
data class NutritionEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val foodName: String,
    val portionSize: String,
    val calories: Int,
    val nutrients: String?,
    val date: Date = Date()
)

@Entity
data class ScreenTimeEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val appName: String,
    val durationMinutes: Int,
    val date: Date = Date()
)

@Entity
data class ScheduleEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val title: String,
    val details: String?,
    val startTime: Long,
    val endTime: Long,
    val date: Date = Date()
)

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String
)

@Entity
data class HistoryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val feature: String, // e.g. "habit", "task", etc.
    val featureId: Int,
    val completedOn: Date = Date()
)
