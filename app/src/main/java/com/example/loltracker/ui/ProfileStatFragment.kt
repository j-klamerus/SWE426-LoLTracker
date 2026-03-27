package com.example.loltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loltracker.ui.ProfileStatFragmentArgs
import com.example.loltracker.databinding.FragmentProfileStatBinding
import androidx.navigation.fragment.navArgs

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

        val message = args.account
        val puuid = args.puuid
        viewModel.searchMatchIDS(puuid)

        viewModel.matchDataResults.observe(viewLifecycleOwner) { matches->
            if(matches != null) {
                binding.recentGames.text = matches.toString()
            }
        }

        // Put message text into displayAccount
        binding.displayAccountView.text = message
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}