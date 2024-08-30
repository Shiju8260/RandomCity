package com.fullcreative.randomcity.presentation.details

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.fullcreative.randomcity.domain.models.CityAndColor
import com.fullcreative.randomcity.domain.worker.ToastWorker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import java.util.concurrent.TimeUnit

@Composable
fun DetailsScreen(cityAndColor: CityAndColor, onBackPress: () -> Unit) {
    val cityLatLng = remember {
        mutableStateOf(
            LatLng(cityAndColor.city.latitude, cityAndColor.city.longitude)
        )
    }
    val context = LocalContext.current
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
        startWorker(context,cityAndColor.city.name)
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

fun startWorker(context: Context,cityName: String) {
    val workManager = WorkManager.getInstance(context)

    val inputData = Data.Builder()
        .putString("city_name", cityName)
        .build()

    val toastWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<ToastWorker>()
        .setInitialDelay(5, TimeUnit.SECONDS) // Set the delay to 5 seconds
        .setInputData(inputData)
        .build()

    workManager.enqueue(toastWorkRequest)
}
