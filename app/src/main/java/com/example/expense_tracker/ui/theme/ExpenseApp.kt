package com.example.expense_tracker.ui.theme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// -------- Navigation destinations --------

sealed class ExpenseScreen(val route: String) {
    object Home : ExpenseScreen("home")
    object Add : ExpenseScreen("add")
}

// -------- Root app composable: sets up NavHost --------

@Composable
fun ExpenseApp(
    viewModel: ExpenseViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ExpenseScreen.Home.route
    ) {
        composable(ExpenseScreen.Home.route) {
            ExpenseHomeScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate(ExpenseScreen.Add.route) }
            )
        }
        composable(ExpenseScreen.Add.route) {
            AddExpenseScreen(
                viewModel = viewModel,
                onDone = { navController.popBackStack() }
            )
        }
    }
}