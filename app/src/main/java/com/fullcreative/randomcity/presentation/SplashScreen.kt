package com.fullcreative.randomcity.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SplashScreen(name: String) {
    Text(
        text = "Hello $name!"
    )
}