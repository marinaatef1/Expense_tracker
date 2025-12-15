package com.example.expense_tracker

import com.example.expense_tracker.data.Expense
import com.example.expense_tracker.data.ExpenseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeExpenseDao : ExpenseDao {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())

    override fun getAllExpenses(): Flow<List<Expense>> {
        return _expenses
    }

    override suspend fun insertExpense(expense: Expense): Long {
        val currentList = _expenses.value
        val maxId = currentList.maxOfOrNull { it.id } ?: 0
        val newExpense = expense.copy(id = maxId + 1)
        _expenses.value = currentList + newExpense
        return newExpense.id.toLong()
    }

    override suspend fun updateExpense(expense: Expense) {
        val newList = _expenses.value.toMutableList()
        val index = newList.indexOfFirst { it.id == expense.id }
        if (index != -1) {
            newList[index] = expense
            _expenses.value = newList
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        _expenses.value = _expenses.value.filterNot { it.id == expense.id }
    }

    override suspend fun deleteAll() {
        _expenses.value = emptyList()
    }
}
