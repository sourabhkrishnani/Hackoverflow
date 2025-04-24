package com.example.scheduleassistant.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.scheduleassistant.data.dao.*
import com.example.scheduleassistant.data.models.*
import androidx.room.TypeConverter
import java.util.Date

@Database(
    entities = [Habit::class, Task::class, Expense::class, Earning::class, WaterIntake::class, NutritionEntry::class, ScreenTimeEntry::class, ScheduleEntry::class, Category::class, HistoryEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun taskDao(): TaskDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun earningDao(): EarningDao
    abstract fun waterIntakeDao(): WaterIntakeDao
    abstract fun nutritionEntryDao(): NutritionEntryDao
    abstract fun screenTimeEntryDao(): ScreenTimeEntryDao
    abstract fun scheduleEntryDao(): ScheduleEntryDao
    abstract fun categoryDao(): CategoryDao
    abstract fun historyEntryDao(): HistoryEntryDao
}

// Type converter for java.util.Date
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
