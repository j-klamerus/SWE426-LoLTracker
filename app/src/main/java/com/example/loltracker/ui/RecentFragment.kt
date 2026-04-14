package com.example.loltracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.loltracker.dao.RecentDatabase
import com.example.loltracker.databinding.FragmentRecentBinding
import kotlin.getValue

class RecentFragment : Fragment() {
    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Create DAO for ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dao = RecentDatabase.getInstance(application).recentDao
        val viewModelFactory = RecentViewModelFactory(dao)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(RecentViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}