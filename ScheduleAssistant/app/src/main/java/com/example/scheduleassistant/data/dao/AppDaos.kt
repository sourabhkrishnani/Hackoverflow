package com.example.scheduleassistant.data.dao

import androidx.room.*
import com.example.scheduleassistant.data.models.*

@Dao
interface HabitDao {
    @Insert suspend fun insert(habit: Habit): Long
    @Update suspend fun update(habit: Habit)
    @Delete suspend fun delete(habit: Habit)
    @Query("SELECT * FROM Habit") suspend fun getAll(): List<Habit>
}

@Dao
interface TaskDao {
    @Insert suspend fun insert(task: Task): Long
    @Update suspend fun update(task: Task)
    @Delete suspend fun delete(task: Task)
    @Query("SELECT * FROM Task") suspend fun getAll(): List<Task>
}

@Dao
interface ExpenseDao {
    @Insert suspend fun insert(expense: Expense): Long
    @Update suspend fun update(expense: Expense)
    @Delete suspend fun delete(expense: Expense)
    @Query("SELECT * FROM Expense") suspend fun getAll(): List<Expense>
}

@Dao
interface EarningDao {
    @Insert suspend fun insert(earning: Earning): Long
    @Update suspend fun update(earning: Earning)
    @Delete suspend fun delete(earning: Earning)
    @Query("SELECT * FROM Earning") suspend fun getAll(): List<Earning>
}

@Dao
interface WaterIntakeDao {
    @Insert suspend fun insert(water: WaterIntake): Long
    @Update suspend fun update(water: WaterIntake)
    @Delete suspend fun delete(water: WaterIntake)
    @Query("SELECT * FROM WaterIntake") suspend fun getAll(): List<WaterIntake>
}

@Dao
interface NutritionEntryDao {
    @Insert suspend fun insert(entry: NutritionEntry): Long
    @Update suspend fun update(entry: NutritionEntry)
    @Delete suspend fun delete(entry: NutritionEntry)
    @Query("SELECT * FROM NutritionEntry") suspend fun getAll(): List<NutritionEntry>
}

@Dao
interface ScreenTimeEntryDao {
    @Insert suspend fun insert(entry: ScreenTimeEntry): Long
    @Update suspend fun update(entry: ScreenTimeEntry)
    @Delete suspend fun delete(entry: ScreenTimeEntry)
    @Query("SELECT * FROM ScreenTimeEntry") suspend fun getAll(): List<ScreenTimeEntry>
}

@Dao
interface ScheduleEntryDao {
    @Insert suspend fun insert(entry: ScheduleEntry): Long
    @Update suspend fun update(entry: ScheduleEntry)
    @Delete suspend fun delete(entry: ScheduleEntry)
    @Query("SELECT * FROM ScheduleEntry") suspend fun getAll(): List<ScheduleEntry>
}

@Dao
interface CategoryDao {
    @Insert suspend fun insert(category: Category): Long
    @Update suspend fun update(category: Category)
    @Delete suspend fun delete(category: Category)
    @Query("SELECT * FROM Category") suspend fun getAll(): List<Category>
}

@Dao
interface HistoryEntryDao {
    @Insert suspend fun insert(entry: HistoryEntry): Long
    @Query("SELECT * FROM HistoryEntry WHERE completedOn BETWEEN :start AND :end")
    suspend fun getByDateRange(start: java.util.Date, end: java.util.Date): List<HistoryEntry>
    @Query("SELECT * FROM HistoryEntry WHERE feature = :feature AND featureId = :featureId")
    suspend fun getFeatureHistory(feature: String, featureId: Int): List<HistoryEntry>
    @Query("SELECT * FROM HistoryEntry") suspend fun getAll(): List<HistoryEntry>
}
