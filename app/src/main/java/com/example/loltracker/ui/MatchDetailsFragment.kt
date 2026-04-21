package com.example.loltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.loltracker.data.SummonerMatchData
import com.example.loltracker.databinding.FragmentMatchDetailsBinding
import com.example.loltracker.network.championIconUrl
import com.example.loltracker.network.itemIconUrl
import com.example.loltracker.network.spellIconUrl

class MatchDetailsFragment : Fragment() {

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MatchDetailsFragmentArgs by navArgs()
    private val viewModel: MatchDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerBlueTeam.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRedTeam.layoutManager = LinearLayoutManager(requireContext())

        viewModel.matchData.observe(viewLifecycleOwner) { match ->
            if (match != null) {
                bindMatch(match)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrBlank()) {
                binding.tvQueueType.text = error
            }
        }

        viewModel.loadMatch(args.matchId, args.region)
    }

    private fun bindMatch(match: SummonerMatchData) {
        binding.tvQueueType.text = match.info.gameMode

        val durationMinutes = match.info.gameDuration / 60
        val durationSeconds = match.info.gameDuration % 60
        binding.tvGameDuration.text = "${durationMinutes}m ${durationSeconds}s"

        val blueTeam = match.info.participants.filter {it.teamId == 100}
        val redTeam = match.info.participants.filter {it.teamId == 200}

        val focusedPlayer = match.info.participants.firstOrNull { it.puuid == args.puuid } ?: match.info.participants.first()
        // not taken from actual focused player, needs to take puuid from match history page to show as intended

        binding.tvMatchResult.text = if (focusedPlayer.win) "Victory" else "Defeat"
        binding.tvSummonerName.text = focusedPlayer.summonerName
        binding.tvChampionLane.text = "${focusedPlayer.championName} • ${focusedPlayer.teamPosition}"
        binding.tvKDA.text = "${focusedPlayer.kills} / ${focusedPlayer.deaths} / ${focusedPlayer.assists}"
        binding.tvCS.text = "${focusedPlayer.totalMinionsKilled} CS"
        binding.tvGold.text = "${focusedPlayer.goldEarned} Gold"
        binding.tvDamage.text = "Damage: ${focusedPlayer.totalDamageDealtToChampions}"
        binding.tvVisionScore.text = "Vision Score: ${focusedPlayer.visionScore}"


        binding.imgChampionIcon.load(championIconUrl(focusedPlayer.championName))


        spellIconUrl(focusedPlayer.summoner1Id)?.let {binding.imgSpell1.load(it)}
        spellIconUrl(focusedPlayer.summoner2Id)?.let {binding.imgSpell2.load(it)}


        loadItem(binding.item0, focusedPlayer.item0)
        loadItem(binding.item1, focusedPlayer.item1)
        loadItem(binding.item2, focusedPlayer.item2)
        loadItem(binding.item3, focusedPlayer.item3)
        loadItem(binding.item4, focusedPlayer.item4)
        loadItem(binding.item5, focusedPlayer.item5)
        loadItem(binding.item6, focusedPlayer.item6)

        binding.recyclerBlueTeam.adapter = PlayerAdapter(blueTeam)
        binding.recyclerRedTeam.adapter = PlayerAdapter(redTeam)
    }

    private fun loadItem(imageView: ImageView, itemId: Int) {
        if (itemId == 0) {
            imageView.setImageDrawable(null)
        } else {
            imageView.load(itemIconUrl(itemId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
