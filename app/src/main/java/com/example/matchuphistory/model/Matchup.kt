package com.example.matchuphistory.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Matchups")
class Matchup(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val date: Date,
        val homeTeam: String,
        val awayTeam: String,
        val homeScore: Int,
        val awayScore: Int,
        val tournament: String
) {
    override fun toString() = "$homeTeam v.s. $awayTeam"
}