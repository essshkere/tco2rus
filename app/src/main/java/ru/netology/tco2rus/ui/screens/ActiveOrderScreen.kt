package ru.netology.tco2rus.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.netology.tco2rus.viewModel.OrdersViewModel

@Composable
fun ActiveOrdersScreen(
    navController: NavController,
    onOrderClick: (Long) -> Unit
) {
    val viewModel: OrdersViewModel = hiltViewModel()
    val orders by viewModel.activeOrders.collectAsState()

    LazyColumn {
        items(orders) { order ->
            Button(onClick = { onOrderClick(order.id) }) {
                Text("Заказ #${order.id}")
            }
        }
    }
}