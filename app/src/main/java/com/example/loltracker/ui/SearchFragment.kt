package com.example.loltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.loltracker.databinding.FragmentSearchBinding
import androidx.lifecycle.lifecycleScope
import com.example.loltracker.network.RiotAccountApi
import com.example.loltracker.network.RiotSummonerApi
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

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

        val apiKey = ""

        viewModel.accountData.observe(viewLifecycleOwner) { data ->
            binding.textView.text = data?.gameName
            binding.textView2.text = data?.tagLine
            binding.textView3.text = data?.puuid
        }

        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            binding.textView4.text = profile?.profileIconId.toString()
            binding.textView5.text = profile?.summonerLevel.toString()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) binding.textView.text = error
        }

        binding.searchButton.setOnClickListener {
            // Prepares nav controller
            val navController = view.findNavController()

            // Grabs the input from the account EditText
            var accountInput = binding.accountInputEdit.text.toString()

            val account = accountInput.split("#")
            val accountUser = account.getOrNull(0) ?: ""
            val accountTag = account.getOrNull(1) ?: ""

            viewModel.searchPlayer(accountUser, accountTag, apiKey)

            }

            /*
            if (account == "latore#soudr") {
                val action = SearchFragmentDirections.actionSearchFragmentToInvalidSearchFragment()
                navController.navigate(action)
            }
            else {
                val action = SearchFragmentDirections.actionSearchFragmentToProfileStatFragment(account)
                navController.navigate(action)
            }
            */


            // Sends user to profile stat fragment after hitting the Search button
            //navController.navigate(R.id.action_searchFragment_to_profileStatFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}