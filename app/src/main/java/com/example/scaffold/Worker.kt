/*
package com.example.scaffoldmanager

import android.content.Context
import androidx.work.CoroutineWorker

import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.net.URL

class WorkManager(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    companion object
    {
        const val KEY_CONTENT_JS = ""
    }
    override suspend fun doWork(): Result {
        return try {
            val file = File(applicationContext.cacheDir, "1.js")
            try{
                var response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                    )

                file.writeBytes(response.toByteArray())
            }catch (e: Exception){
                var response = null

            }

            Result.success(
                workDataOf(
                     KEY_CONTENT_JS to file.absolutePath
                )

            )
        } catch (e: Exception) {
            Result.failure()
        }
    }
}*/
