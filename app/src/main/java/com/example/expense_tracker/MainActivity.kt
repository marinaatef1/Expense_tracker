package com.example.expense_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expense_tracker.ui.theme.ExpenseApp
import com.example.expense_tracker.ui.theme.ExpenseViewModel
import com.example.expense_tracker.ui.theme.ExpenseViewModelFactory
import com.example.expense_tracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val context = LocalContext.current

                    val viewModel: ExpenseViewModel = viewModel<ExpenseViewModel>(
                        factory = ExpenseViewModelFactory(context.applicationContext)
                    )

                    // Root composable with navigation
                    ExpenseApp(viewModel = viewModel)
                }
            }
        }
    }
}