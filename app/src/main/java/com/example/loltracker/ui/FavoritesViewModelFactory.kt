package com.example.loltracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loltracker.dao.FavoritesDao

// Creates view models. Necessary for room database integration
class FavoritesViewModelFactory (private val dao: FavoritesDao) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}