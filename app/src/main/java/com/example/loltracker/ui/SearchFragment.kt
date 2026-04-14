package com.example.loltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.loltracker.databinding.FragmentSearchBinding
import androidx.fragment.app.viewModels
import com.example.loltracker.dao.RecentDatabase
import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.BuildConfig
import androidx.lifecycle.lifecycleScope
import com.example.loltracker.network.RiotAccountApi
import com.example.loltracker.network.RiotSummonerApi
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.ui.SearchFragmentDirections
import com.example.loltracker.ui.SearchViewModel

private var accountData: AccountResponse? = null
private var profileData: ProfileResponse? = null

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val recentViewModel: RecentViewModel by viewModels {
        val dao = RecentDatabase.getInstance(requireContext()).recentDao
        RecentViewModelFactory(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiKey = BuildConfig.apiKey


        viewModel.accountData.observe(viewLifecycleOwner) { data ->
            // Assigns response to var for navigation
            accountData = data

            /*
            // Outputs to UI for testing purposes
            binding.textView.text = data?.gameName
            binding.textView2.text = data?.tagLine
            binding.textView3.text = data?.puuid
            Not currently used
             */

            profileNavigate()
        }

        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            // Assigns response to var for navigation
            profileData = profile

            /*
            // Outputs to UI for testing purposes
            binding.textView4.text = profile?.profileIconId.toString()
            binding.textView5.text = profile?.summonerLevel.toString()
            Not currently used
             */

            profileNavigate()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            // Will need to reimplement when visible errors are implemented
            //if (error != null) binding.textView.text = error
        }

        binding.searchButton.setOnClickListener {
            // Prepares nav controller
            val navController = view.findNavController()

            // Grabs the input from the account EditText
            var accountInput = binding.accountInputEdit.text.toString()
            var regionInput = binding.searchSpinner.selectedItem.toString()

            val account = accountInput.split("#")
            val accountUser = account.getOrNull(0) ?: ""
            val accountTag = account.getOrNull(1) ?: ""

            addRecent(accountInput, regionInput)

            viewModel.searchPlayer(accountUser, accountTag, apiKey)

            }

        binding.favoriteButton.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToFavoritesFragment())
        }

        binding.recentButton.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToRecentFragment())
        }
            viewModel.searchPlayer(accountUser, accountTag, apiKey)

            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Function is ran during viewModel's data collection. Only transitions to profile fragment if all data is present
    private fun profileNavigate() {
        val account = accountData
        val profile = profileData

        // Both are set to null and get updated as data comes in. This checks to make sure they both received data.
        if (account != null && profile != null) {
            val action = SearchFragmentDirections.actionSearchFragmentToProfileStatFragment(
                account = "${account.gameName}#${account.tagLine}",
                puuid = account.puuid,
                profileIconId = profile.profileIconId,
                summonerLevel = profile.summonerLevel
            )
            findNavController().navigate(action)
        }
    }
    // Takes data from search and assigns it to values in RecentViewModel.kt
    // Calls addRecent function which calls Dao insert function
    private fun addRecent(accountInput: String, regionInput: String) {
        recentViewModel.newRecentName = accountInput
        recentViewModel.newRegion = regionInput
        recentViewModel.addRecent()
    }
}

