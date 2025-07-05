package ru.netology.tco2rus.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = "", onValueChange = {}, label = { Text("Логин") })
        TextField(value = "", onValueChange = {}, label = { Text("Пароль") })
        Button(onClick = onLoginSuccess) {
            Text("Войти")
        }
    }
}