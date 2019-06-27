package com.example.matchuphistory.model

import androidx.lifecycle.LiveData

class MainRepository private constructor(private val matchupsDao: MatchupsDao) {

    val teams: LiveData<List<String>> = matchupsDao.getTeams()

    fun getMatchups(team1: String, team2: String): LiveData<List<Matchup>> {
        return matchupsDao.getMatchups(team1, team2)
    }

    fun getOpponents(team: String): LiveData<List<String>> {
        return matchupsDao.getOpponents(team)
    }

    companion object {
        @Volatile
        private var INSTANCE: MainRepository? = null

        fun getInstance(matchupsDao: MatchupsDao) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MainRepository(matchupsDao).also { INSTANCE = it }
                }
    }
}
