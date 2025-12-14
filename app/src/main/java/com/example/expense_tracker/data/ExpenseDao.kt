package com.example.expense_tracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object for Expense table
@Dao
interface ExpenseDao {

    // Observe all expenses sorted by newest first
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // Insert a new expense
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

    // Update existing expense
    @Update
    suspend fun updateExpense(expense: Expense)

    // Delete one expense
    @Delete
    suspend fun deleteExpense(expense: Expense)

    // Delete all (we may use this for clearing data)
    @Query("DELETE FROM expenses")
    suspend fun deleteAll()
}