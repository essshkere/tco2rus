package ru.netology.tco2rus.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    onOrdersClick: () -> Unit
) {
    Button(onClick = onOrdersClick) {
        Text("Перейти к заказам")
    }
}