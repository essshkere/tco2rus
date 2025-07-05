package ru.netology.tco2rus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    orderId: Long,
    onBackClick: () -> Unit
) {
    Column {
        Text("Детали заказа #$orderId")
        Button(onClick = onBackClick) {
            Text("Назад")
        }
    }
}