package com.example.loltracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loltracker.dao.RecentDao

// Creates view models. Necessary for room database integration
class RecentViewModelFactory (private val dao: RecentDao) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(RecentViewModel::class.java)){
            return RecentViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}