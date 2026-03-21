package com.example.loltracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loltracker.databinding.FragmentProfileStatBinding

class ProfileStatFragment : Fragment() {

    private var _binding: FragmentProfileStatBinding? = null
    private val binding get() = _binding!!

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

        // Account is saved in bundle when search is clicked. This retrieves it and assigns to val message
        val message = ProfileStatFragmentArgs.fromBundle(requireArguments()).account

        // Put message text into displayAccount
        binding.displayAccountView.text = message
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







/*
Keeping this in case it's needed. Prebinding

override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_stat, container, false)
    }
    */