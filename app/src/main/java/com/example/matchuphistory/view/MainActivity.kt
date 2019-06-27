package com.example.matchuphistory.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.matchuphistory.R
import com.example.matchuphistory.adapters.MatchupAdapter
import com.example.matchuphistory.databinding.ActivityMainBinding
import com.example.matchuphistory.viewmodel.MainViewModel
import com.example.matchuphistory.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG by lazy { MainActivity::class.java.simpleName }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val adapter = MatchupAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = obtainViewModel(this)
        mainViewModel.matchups.observe(this, Observer { matchups ->
            Log.i(TAG, "matchups size=${matchups.size}")
            adapter.setMatchups(matchups)
            adapter.notifyDataSetChanged()
        })

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }
        binding.matchups.adapter = adapter
        binding.matchups.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    companion object {

        fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }
}
