package com.example.matchuphistory.viewmodel

import androidx.databinding.ObservableField
import com.example.matchuphistory.model.Matchup
import com.example.matchuphistory.util.DATE_FORMAT
import java.text.SimpleDateFormat

class MatchupViewModel(matchup: Matchup) {
    var date = ObservableField<String>()
    var score1 = ObservableField<String>()
    var score2 = ObservableField<String>()
    var competition = ObservableField<String>()

    init {
        setMatchup(matchup)
    }

    fun setMatchup(matchup: Matchup) {
        score1.set(String.format("%s %d", matchup.homeTeam, matchup.homeScore))
        score2.set(String.format("%s %d", matchup.awayTeam, matchup.awayScore))
        date.set(SimpleDateFormat(DATE_FORMAT).format(matchup.date))
        competition.set(matchup.tournament)
    }
}
