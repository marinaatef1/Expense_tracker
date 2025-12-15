package com.example.expense_tracker.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expense_tracker.data.Expense
import com.example.expense_tracker.data.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.text.category

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    // 🔹 State for the category filter
    private val _categoryFilter = MutableStateFlow("All")
    val categoryFilter: StateFlow<String> = _categoryFilter

    // All expenses from the repository
    private val allExpenses: StateFlow<List<Expense>> =
        repository.expenses
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    // 🔹 Categories for the filter dropdown
    val categories: StateFlow<List<String>> =
        allExpenses
            .map { expenses ->
                listOf("All") + expenses.map { it.category }.distinct()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf("All")
            )

    // 🔹 Filtered expenses based on the selected category
    val expenses: StateFlow<List<Expense>> =
        combine(allExpenses, categoryFilter) { expenses, category ->
            if (category == "All") {
                expenses
            } else {
                expenses.filter { it.category == category }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )


    // 🔹 Total amount of all expenses (derived from the list above)
    val totalAmount: StateFlow<Double> =
        expenses // This will now be the filtered list
            .map { list -> list.sumOf { it.amount } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0.0
            )

    // 🔹 Function to update the filter
    fun setCategoryFilter(category: String) {
        _categoryFilter.value = category
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.updateExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}