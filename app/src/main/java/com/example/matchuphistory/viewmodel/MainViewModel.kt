package com.example.matchuphistory.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.matchuphistory.R
import com.example.matchuphistory.model.MainRepository
import com.example.matchuphistory.model.Matchup

class MainViewModel(private val context: Context, private val repository: MainRepository) : ViewModel() {
    private val TAG by lazy { MainViewModel::class.java.simpleName }
    var resultVisibility: LiveData<Int>
    var infoMessageVisibility: LiveData<Int>
    var summary: LiveData<String>
    var matchups: LiveData<List<Matchup>>
    var teams: LiveData<List<String>> = repository.teams

    var opponents: LiveData<List<String>>

    var team1 = MutableLiveData<String>().apply { value = "" }
    var team2 = ""
    var infoMessage = MutableLiveData<String>().apply {
        value = context.getString(R.string.default_info_message)
    }
    var progressVisibility = MutableLiveData<Int>().apply {
        value = View.GONE
    }
    private val query = MutableLiveData<Array<String>>().apply {
        value = arrayOf(team1.value!!, team2)
    }

    init {
        matchups = Transformations.switchMap(query) {
            repository.getMatchups(it[0], it[1])
        }

        opponents = Transformations.switchMap(team1) {
            repository.getOpponents(it)
        }
        summary = Transformations.map(matchups) {
            var won = 0
            var lost = 0
            var drawn = 0
            for (matchup in it) {
                if (matchup.homeScore > matchup.awayScore) {
                    if (matchup.homeTeam == team1.value) {
                        won++
                    } else {
                        lost++
                    }
                } else if (matchup.homeScore < matchup.awayScore) {
                    if (matchup.homeTeam == team1.value) {
                        lost++
                    } else {
                        won++
                    }
                } else {
                    drawn++
                }
            }
            String.format(this@MainViewModel.context.getString(R.string.summary), team1.value, won, lost, drawn)
        }

        resultVisibility = Transformations.map(matchups) {
            progressVisibility.value = View.GONE
            if (it.isEmpty())
                View.GONE
            else
                View.VISIBLE
        }
        infoMessageVisibility = Transformations.map(matchups) {
            if (it.isEmpty())
                View.VISIBLE
            else
                View.GONE
        }
    }

    fun onItemTeam1Changed(team: String) {
        if (team1.value != team)
            team1.value = team
    }

    fun onItemTeam2Changed(team: String) {
        team2 = team
    }

    fun onQueryClick() {
        Log.i(TAG, "query ${team1.value} v.s. $team2");

        progressVisibility.value = View.VISIBLE
        infoMessage.value = context.getString(R.string.error_info_message)
        query.value = arrayOf(team1.value!!, team2)

    }
}
