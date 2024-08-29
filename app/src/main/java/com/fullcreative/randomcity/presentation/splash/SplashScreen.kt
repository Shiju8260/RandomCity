package com.fullcreative.randomcity.presentation.splash

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
import androidx.navigation.NavController
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SplashScreen(cityAndColor: CityAndColor? = null, navController: NavController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(cityAndColor) {
        cityAndColor?.let {
            navController.navigate("main")
        }
    }

    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = "Full Creative",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}