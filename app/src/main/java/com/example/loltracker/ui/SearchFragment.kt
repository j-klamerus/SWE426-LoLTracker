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

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        val apiKey = "RGAPI-9b77c0ab-4f60-4723-90b5-66d9c4dfc106"

        binding.searchButton.setOnClickListener {
            // Prepares nav controller
            val navController = view.findNavController()

            // Grabs the input from the account EditText
            var accountInput = binding.accountInputEdit.text.toString()

            val account = accountInput.split("#")
            val accountUser = account.getOrNull(0) ?: ""
            val accountTag = account.getOrNull(1) ?: ""

            // Test code to make sure trim/splitting works
            binding.textView.text = accountUser.trim().toString()
            binding.textView2.text = accountTag.trim().toString()

            lifecycleScope.launch {
                try {
                    val accountResponse = RiotAccountApi.api.getAccountPUUID(
                        accountUser,
                        accountTag,
                        apiKey
                    )

                    if (accountResponse.isSuccessful) {
                        val data = accountResponse.body()
                        //val puuid = data?.puuid
                        binding.textView.text = data?.gameName
                        binding.textView2.text = data?.tagLine
                        binding.textView3.text = data?.puuid

                        val profileResponse = RiotSummonerApi.api.getAccountProfile(
                            binding.textView3.text.toString(),
                            apiKey
                        )

                        if (profileResponse.isSuccessful) {
                            val profileData = profileResponse.body()
                            binding.textView4.text = profileData?.profileIconId
                            binding.textView5.text = profileData?.summonerLevel.toString()
                        } else {
                            binding.textView.text = "Profile Error: ${profileResponse.code()}"
                        }

                    } else {
                        binding.textView2.text = "Account Error: ${accountResponse.code()}"
                    }

                } catch (e: Exception) {
                    binding.textView3.text = "Exception: ${e.message}"
                }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}