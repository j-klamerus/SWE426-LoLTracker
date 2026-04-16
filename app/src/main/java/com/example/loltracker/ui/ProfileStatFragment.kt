package com.example.loltracker.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.loltracker.databinding.FragmentProfileStatBinding
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.loltracker.network.championIconUrl
import com.example.loltracker.network.userIconUrl
import kotlin.math.floor
import com.google.android.material.color.MaterialColors

class ProfileStatFragment : Fragment() {

    private var _binding: FragmentProfileStatBinding? = null
    private val binding get() = _binding!!
    //this grabs the arguments passed when navigating to this fragment
    private val args: ProfileStatFragmentArgs by navArgs()

    //grab viewmodel
    private val viewModel: ProfileStatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileStatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountLevelText.text = "Level:  ${args.summonerLevel.toString()}"
        binding.profileIconImage.load(userIconUrl(args.profileIconId.toString()))
        val message = args.account
        val puuid = args.puuid
        viewModel.searchMatchIDS(puuid)

        // --------------------------------------------------------------------------------------------------
        // Match Data Loading and Formatting
        // --------------------------------------------------------------------------------------------------

        viewModel.matchDataHistory.observe(viewLifecycleOwner) { matches ->
            // Lists are used to reduce the amount of repeated code
            var i = 0
            val winText = listOf(binding.winText1, binding.winText2, binding.winText3, binding.winText4, binding.winText5)
            val matchLayout = listOf(binding.matchLayout1, binding.matchLayout2, binding.matchLayout3, binding.matchLayout4, binding.matchLayout5)
            val viewMatchButton = listOf(binding.viewMatchButton1, binding.viewMatchButton2, binding.viewMatchButton3, binding.viewMatchButton4, binding.viewMatchButton5)
            val timeText = listOf(binding.timeText1, binding.timeText2, binding.timeText3, binding.timeText4, binding.timeText5)
            val champIcon = listOf(binding.champIcon1, binding.champIcon2, binding.champIcon3, binding.champIcon4, binding.champIcon5)
            val kdaText = listOf(binding.kdaText1, binding.kdaText2, binding.kdaText3, binding.kdaText4, binding.kdaText5)
            val secondaryColor = MaterialColors.getColor(requireView(), com.google.android.material.R.attr.colorSecondary)
            val primaryColor = MaterialColors.getColor(requireView(), com.google.android.material.R.attr.colorPrimary)

            // Runs through five times. Each one references the next game in the list
            while (i < 5) {
                // Gets game data for specific match in list
                val game = matches.getOrNull(i) ?: return@observe

                // android:backgroundTint="?attr/colorPrimary"
                // android:background="?attr/colorSecondary">

                // Assigns W/L value to UI. Also changes layout colors to be based on W/L/R
                // Cannot currently handle remakes. Need to revisit
                if (game.win == true) {
                    winText[i].text = "Victory"
                    matchLayout[i].setBackgroundColor(secondaryColor)
                    //viewMatchButton[i].backgroundTintList = ColorStateList.valueOf(primaryColor)
                }
                else {
                    winText[i].text = "Defeat"
                    matchLayout[i].setBackgroundColor(primaryColor)
                    //viewMatchButton[i].backgroundTintList = ColorStateList.valueOf(secondaryColor)
                }

                // Convert game duration from seconds to M/S
                val gameTime = game.gameDuration
                if (gameTime > 60) {
                    val min = floor((gameTime / 60).toDouble()).toInt()
                    val sec = gameTime - (min * 60)

                    timeText[i].text = "${min}m ${sec}s"
                }
                else { timeText[i].text ="${gameTime}s" }

                // Display champ profile picture
                champIcon[i].load(championIconUrl(game.championName.toString()))

                // Format and display K/D/A
                val kills = game.kills
                val deaths = game.deaths
                val assists = game.assists

                kdaText[i].text = "${kills} / ${deaths} / ${assists}"

                i++
            }
        }

        // --------------------------------------------------------------------------------------------------
        // Button Listeners and Match Detail Navigation
        // --------------------------------------------------------------------------------------------------
        // NOTE: I tried to make this automated, but it ended up crashing sometimes - CL
        binding.viewMatchButton1.setOnClickListener {
            val match = viewModel.matchDataHistory.value?.getOrNull(0)
            val matchID = match?.matchId

            findNavController().navigate(ProfileStatFragmentDirections.actionProfileStatFragmentToMatchDetailsFragment(matchID.toString(), puuid))
        }

        binding.viewMatchButton2.setOnClickListener {
            val match = viewModel.matchDataHistory.value?.getOrNull(1)
            val matchID = match?.matchId

            findNavController().navigate(ProfileStatFragmentDirections.actionProfileStatFragmentToMatchDetailsFragment(matchID.toString(), puuid))
        }

        binding.viewMatchButton3.setOnClickListener {
            val match = viewModel.matchDataHistory.value?.getOrNull(2)
            val matchID = match?.matchId

            findNavController().navigate(ProfileStatFragmentDirections.actionProfileStatFragmentToMatchDetailsFragment(matchID.toString(), puuid))
        }

        binding.viewMatchButton4.setOnClickListener {
            val match = viewModel.matchDataHistory.value?.getOrNull(3)
            val matchID = match?.matchId

            findNavController().navigate(ProfileStatFragmentDirections.actionProfileStatFragmentToMatchDetailsFragment(matchID.toString(), puuid))
        }

        binding.viewMatchButton5.setOnClickListener {
            val match = viewModel.matchDataHistory.value?.getOrNull(4)
            val matchID = match?.matchId

            findNavController().navigate(ProfileStatFragmentDirections.actionProfileStatFragmentToMatchDetailsFragment(matchID.toString(), puuid))
        }





        // Put message text into displayAccount
        binding.accountNameText.text = message
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}