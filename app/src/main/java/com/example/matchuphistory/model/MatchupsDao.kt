package com.example.matchuphistory.model


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the Matchups table.
 */
@Dao
interface MatchupsDao {
    @Query("SELECT DISTINCT homeTeam FROM Matchups " +
            "UNION " +
            "SELECT DISTINCT awayTeam FROM Matchups ORDER BY awayTeam ASC")
    fun getTeams(): LiveData<List<String>>

    @Query("SELECT * FROM Matchups WHERE (homeTeam = :team1 AND awayTeam = :team2)" +
            " OR (homeTeam = :team2 AND awayTeam = :team1) ORDER BY date DESC")
    fun getMatchups(team1: String, team2: String): LiveData<List<Matchup>>

    @Query("SELECT DISTINCT homeTeam FROM Matchups WHERE (awayTeam = :team)" +
            "UNION " +
            "SELECT DISTINCT awayTeam FROM Matchups WHERE (homeTeam = :team) ORDER BY awayTeam ASC")
    fun getOpponents(team: String): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Matchup>)
}
