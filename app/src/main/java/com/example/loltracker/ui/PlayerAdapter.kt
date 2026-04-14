package com.example.loltracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loltracker.R
import com.example.loltracker.databinding.ItemPlayerRowBinding
import com.example.loltracker.model.Participant
import android.widget.ImageView
import coil.load
import com.example.loltracker.ui.championIconUrl
import com.example.loltracker.ui.itemIconUrl
import com.example.loltracker.ui.spellIconUrl
class PlayerAdapter(
    private val players: List<Participant>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(val binding: ItemPlayerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val binding = holder.binding

        binding.tvRowSummonerName.text = player.summonerName
        binding.tvRowChampionRole.text = "${player.championName} • ${player.teamPosition}"
        binding.tvRowKDA.text = "${player.kills} / ${player.deaths} / ${player.assists}"

        binding.imgRowChampionIcon.load(championIconUrl(player.championName))

        loadItem(binding.rowItem0, player.item0)
        loadItem(binding.rowItem1, player.item1)
        loadItem(binding.rowItem2, player.item2)
        loadItem(binding.rowItem3, player.item3)
        loadItem(binding.rowItem4, player.item4)
        loadItem(binding.rowItem5, player.item5)
        loadItem(binding.rowItem6, player.item6)
    }
    private fun loadItem(imageView: ImageView, itemId: Int) {
        if (itemId == 0) {
            imageView.setImageDrawable(null)
        } else {
            imageView.load(itemIconUrl(itemId))
        }
    }

}