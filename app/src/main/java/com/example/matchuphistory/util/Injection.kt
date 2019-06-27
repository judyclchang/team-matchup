package com.example.matchuphistory.util

import android.content.Context
import com.example.matchuphistory.model.MainRepository
import com.example.matchuphistory.model.MatchupDatabase

object Injection {
    fun provideMainRepository(context: Context): MainRepository {
        val database = MatchupDatabase.getInstance(context)
        return MainRepository.getInstance(database.matchupsDao())
    }
}
