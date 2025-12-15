package com.example.expense_tracker.ui.theme
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expense_tracker.data.ExpenseDatabase
import com.example.expense_tracker.data.ExpenseRepository

class ExpenseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            val db = ExpenseDatabase.getInstance(context)
            val repo = ExpenseRepository(db.expenseDao())
            return ExpenseViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}