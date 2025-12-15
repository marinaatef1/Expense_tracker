package com.example.expense_tracker

import com.example.expense_tracker.data.Expense
import com.example.expense_tracker.data.ExpenseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ExpenseRepositoryTest {

    @Test
    fun addExpense_savesCorrectly() = runBlocking {
        // Use our fake DAO instead of Room
        val dao = FakeExpenseDao()
        val repo = ExpenseRepository(dao)

        val expense = Expense(
            title = "Coffee",
            amount = 15.0,
            category = "Food",
            date = 123456L,
            note = "Morning drink"
        )

        // WHEN
        repo.addExpense(expense)

        // THEN – get the list from the Flow
        val list = repo.expenses.first()

        assertEquals(1, list.size)
        assertEquals("Coffee", list[0].title)
    }
}