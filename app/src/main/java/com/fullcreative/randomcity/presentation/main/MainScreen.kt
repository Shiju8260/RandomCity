package com.fullcreative.randomcity.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(cityAndColor: CityAndColor? = null, viewModel: MainScreenViewModel = viewModel()) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
    }
    LaunchedEffect(cityAndColor) {
        cityAndColor?.let {
            viewModel.onEvent(MainScreenEvents.UpdateCityList(it))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Random City", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )

        },
        content = { ip ->
            val state by viewModel.state.collectAsState()
            LazyColumn(
                modifier = Modifier
                    .padding(ip).padding(16.dp)
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
                            headlineColor = emission.color,
                            supportingColor = Color.Black
                        ),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
        }
    )
}


