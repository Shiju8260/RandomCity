package com.fullcreative.randomcity.presentation.main

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.presentation.details.DetailsScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    cityAndColor: CityAndColor? = null,
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController,
    onSelectedCity: (cityAndColor: CityAndColor) -> Unit,
    clearCityState: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
        if(!state.getFromDB) {
            viewModel.onEvent(MainScreenEvents.GetSavedCityList)
        }
    }
    LaunchedEffect(cityAndColor) {
        cityAndColor?.let {
            viewModel.onEvent(MainScreenEvents.UpdateCityList(it))
            clearCityState()
        }
    }

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val isLandscape = remember {
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
    val isTablet = remember { isTabletDevice(context) }

    if (isTablet && isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            val selectedCity = remember {
                mutableStateOf<CityAndColor?>(null)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {


                // Master view on the left
                MainDetailScreen(viewModel, onListClick = { city ->
                    onSelectedCity(city)
                    selectedCity.value = city
                })
            }


            // Details view on the right
            selectedCity.value?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    DetailsScreen(cityAndColor = it, onBackPress = {
                        navController.navigateUp()
                    })
                }
            }
        }
    } else {
        // Just show the master view
        MainDetailScreen(viewModel, onListClick = { city ->
            onSelectedCity(city)
            navController.navigate("details")
        })
    }

}

fun isTabletDevice(context: Context): Boolean {
    val metrics = context.resources.displayMetrics
    val widthDp = metrics.widthPixels / metrics.density
    val heightDp = metrics.heightPixels / metrics.density
    val screenSizeDp = Math.sqrt((widthDp * widthDp + heightDp * heightDp).toDouble())

    return screenSizeDp >= 600 // Consider device a tablet if the screen size is 600dp or larger
}

@Composable
fun MainDetailScreen(
    viewModel: MainScreenViewModel,
    onListClick: (cityAndColor: CityAndColor) -> Unit
) {

    val state by viewModel.state.collectAsState()
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        itemsIndexed(state.cityAndColorList) { index, emission ->
            ListItem(
                headlineContent = {
                    Text(
                        text = emission.city.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                shadowElevation = 5.dp,
                supportingContent = { Text(text = emission.time) },
                colors = ListItemDefaults.colors(
                    containerColor = Color.White,
                    headlineColor = Color(emission.color),
                    supportingColor = Color.Black
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        onListClick(emission)
                    }
            )
        }
    }


}


