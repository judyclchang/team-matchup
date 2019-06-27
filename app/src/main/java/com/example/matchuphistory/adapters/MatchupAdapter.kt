package com.example.matchuphistory.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.matchuphistory.R
import com.example.matchuphistory.databinding.ItemMatchupBinding
import com.example.matchuphistory.model.Matchup
import com.example.matchuphistory.viewmodel.MatchupViewModel

class MatchupAdapter : RecyclerView.Adapter<MatchupAdapter.ItemViewHolder>() {
    private var matchups: List<Matchup> = emptyList()

    fun setMatchups(matchups: List<Matchup>) {
        this.matchups = matchups
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchupAdapter.ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemMatchupBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_matchup,
                parent,
                false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchupAdapter.ItemViewHolder, position: Int) {
        holder.bind(matchups[position])
    }

    override fun getItemCount(): Int {
        return matchups.size
    }

    class ItemViewHolder(private val binding: ItemMatchupBinding) :
            RecyclerView.ViewHolder(binding.root) {

        internal fun bind(matchup: Matchup) {
            with(binding) {
                viewModel = MatchupViewModel(matchup)
                executePendingBindings()
            }
        }
    }
}
