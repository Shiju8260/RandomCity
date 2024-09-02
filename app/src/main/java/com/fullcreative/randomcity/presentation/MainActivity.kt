package com.fullcreative.randomcity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.presentation.details.DetailsScreen
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
        viewModel.onEvent(MainActivityEvents.StartProducer)
        setContent {
            RandomCityTheme {
                AppNavHost(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val state by mainViewModel.state.collectAsState()
    val selectedCity = remember {
        mutableStateOf<CityAndColor?>(null)
    }
    val showAppBar = remember {
        mutableStateOf(false)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        val currentRoute = navBackStackEntry?.destination?.route
        when (currentRoute) {
            "splash" -> {
                showAppBar.value = false
            }

            else -> {
                showAppBar.value = true
            }
        }
    }
    Scaffold(
        topBar = {
            if (showAppBar.value) {
                TopAppBar(
                    title = {
                        Text(
                            text = selectedCity.value?.city?.name ?: "Random City",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(selectedCity.value?.color ?: Color.Black.toArgb()),
                        titleContentColor = Color.White
                    )
                )
            }

        },
        content = { ip ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip)
            ) {
                composable("splash") { SplashScreen(state.cityAndColor, navController) }
                composable("main") {
                    MainScreen(state.cityAndColor, navController = navController, onSelectedCity = {
                        selectedCity.value = it
                    }, clearCityState = {
                        mainViewModel.onEvent(MainActivityEvents.ClearCityState)
                    })
                }
                composable("details") {
                    selectedCity.value?.let {
                        DetailsScreen(it, onBackPress = {
                            selectedCity.value = null
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
    )


}



