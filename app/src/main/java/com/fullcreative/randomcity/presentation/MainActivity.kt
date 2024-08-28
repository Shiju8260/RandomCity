package com.fullcreative.randomcity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.presentation.main.MainScreen
import com.fullcreative.randomcity.presentation.splash.SplashScreen
import com.fullcreative.randomcity.ui.theme.RandomCityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<MainViewModel>()
        viewModel.startProducer()
        setContent {
            RandomCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(innerPadding, viewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(innerPadding: PaddingValues, mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val state by mainViewModel.state.collectAsState()
    val cityAndColor = remember {
        mutableStateOf(CityAndColor())
    }
    LaunchedEffect(state.cityAndColor) {
        state.cityAndColor?.let {
            cityAndColor.value = it
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute == "splash") {
                navController.navigate("main")
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen() }
        composable("main") { MainScreen(cityAndColor.value) }
    }
}



