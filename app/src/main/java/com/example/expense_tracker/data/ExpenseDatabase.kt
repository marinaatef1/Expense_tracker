package com.example.expense_tracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Main Room database for the app
@Database(
    entities = [Expense::class], // list of all tables
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {

    // DAO getter
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        // Singleton – create DB once and reuse
        fun getInstance(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_db"   // file name on device
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}