package com.fullcreative.randomcity.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

    }
}