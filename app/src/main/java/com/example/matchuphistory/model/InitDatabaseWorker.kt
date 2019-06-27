package com.example.matchuphistory.model

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.matchuphistory.util.JSON_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope


class InitDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { InitDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(JSON_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<Matchup>>() {}.type
                    val matchups: List<Matchup> = Gson().fromJson(jsonReader, type)

                    val database = MatchupDatabase.getInstance(applicationContext)
                    database.matchupsDao().insertAll(matchups)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}