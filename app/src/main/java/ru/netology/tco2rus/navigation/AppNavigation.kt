package ru.netology.tco2rus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.netology.tco2rus.ui.screens.ActiveOrdersScreen
import ru.netology.tco2rus.ui.screens.HomeScreen
import ru.netology.tco2rus.ui.screens.LoginScreen
import ru.netology.tco2rus.ui.screens.OrderDetailsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                onOrdersClick = { navController.navigate("orders") }
            )
        }

        composable("orders") {
            ActiveOrdersScreen(
                navController = navController,
                onOrderClick = { orderId -> navController.navigate("order/$orderId") }
            )
        }

        composable(
            route = "order/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            OrderDetailsScreen(
                navController = navController,
                orderId = backStackEntry.arguments?.getLong("id") ?: 0L,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}