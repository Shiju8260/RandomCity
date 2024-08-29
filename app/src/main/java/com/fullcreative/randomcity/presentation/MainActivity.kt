package com.fullcreative.randomcity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.presentation.details.DetailsScreen
import com.fullcreative.randomcity.presentation.main.MainScreen
import com.fullcreative.randomcity.presentation.splash.SplashScreen
import com.fullcreative.randomcity.ui.theme.RandomCityTheme
import com.google.gson.Gson
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

                AppNavHost(viewModel)
            }
        }
    }
}

@Composable
fun AppNavHost(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val state by mainViewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("splash") { SplashScreen(state.cityAndColor, navController) }
        composable("main") { MainScreen(state.cityAndColor, navController = navController) }
        composable(
            "details/{cityAndColor}",
            arguments = listOf(navArgument("cityAndColor") { type = NavType.StringType })
        ) { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("cityAndColor")
            val cityAndColor = userJson?.let { jsonString ->
                Gson().fromJson(jsonString, CityAndColor::class.java)
            }
            DetailsScreen(cityAndColor)
        }
    }
}



