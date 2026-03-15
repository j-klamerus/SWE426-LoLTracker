package com.example.loltracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.loltracker.databinding.FragmentSearchBinding

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

        binding.searchButton.setOnClickListener {
            // Prepares nav controller
            val navController = view.findNavController()

            // Grabs the input from the account EditText and sends it to the profile fragment
            val account = binding.accountInputEdit.text.toString()
            val action = SearchFragmentDirections.actionSearchFragmentToProfileStatFragment(account)
            navController.navigate(action)

            // Sends user to profile stat fragment after hitting the Search button
            //navController.navigate(R.id.action_searchFragment_to_profileStatFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
