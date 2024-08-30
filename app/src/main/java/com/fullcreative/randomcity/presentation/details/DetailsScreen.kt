package com.fullcreative.randomcity.presentation.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun DetailsScreen(cityAndColor: CityAndColor, onBackPress: () -> Unit) {
    val cityLatLng = remember {
        mutableStateOf(
            LatLng(cityAndColor.city.latitude, cityAndColor.city.longitude)
        )
    }
    val cityMarkerState = remember { mutableStateOf(MarkerState(position = cityLatLng.value)) }
    val cameraPositionState = remember {
        mutableStateOf(
            CameraPositionState(
                position = CameraPosition.fromLatLngZoom(
                    cityLatLng.value,
                    10f
                )
            )
        )
    }
    BackHandler {
        onBackPress()
    }
    LaunchedEffect(cityAndColor) {
        cityLatLng.value = LatLng(cityAndColor.city.latitude, cityAndColor.city.longitude)
        cityMarkerState.value = MarkerState(position = cityLatLng.value)
        cameraPositionState.value =
            CameraPositionState(position = CameraPosition.fromLatLngZoom(cityLatLng.value, 10f))

    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState.value
    ) {
        Marker(
            state = cityMarkerState.value,
            title = cityAndColor.city.name,
            snippet = "Marker in " + cityAndColor.city.name
        )
    }


}