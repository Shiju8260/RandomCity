package com.fullcreative.randomcity.domain.worker

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class ToastWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        // Get the city name from the input data
        val cityName = inputData.getString("city_name") ?: return Result.failure()
        Handler(Looper.getMainLooper()).post {
            // Display the Toast message
            Toast.makeText(applicationContext, "Welcome to $cityName", Toast.LENGTH_LONG).show()
        }
        return Result.success()
    }
}