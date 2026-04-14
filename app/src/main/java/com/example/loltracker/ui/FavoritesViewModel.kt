package com.example.loltracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.loltracker.dao.FavoritesDao
import com.example.loltracker.model.Favorites

class FavoritesViewModel(val dao: FavoritesDao) : ViewModel() {
    var newFavoriteName = ""
    var newRegion = MutableLiveData("NA")
    private val favorites = dao.getAll()
    val favoritesString: LiveData<String> = favorites.map{
        favorites -> formatFavorites(favorites)
    }

    fun addFav(){
        viewModelScope.launch{
            val favorites = Favorites()
            favorites.profileName = newFavoriteName
            favorites.region = newRegion.value.toString()
            dao.insert(favorites)
        }
    }

    fun delFav(){
        viewModelScope.launch{
            val favorites = Favorites()
            dao.deleteAll()
        }
    }

    fun formatFavorites(favorites: List<Favorites>): String{
        return favorites.fold(""){
            str, item -> str + '\n' + formatFavorite(item)
        }
    }

    fun formatFavorite(favorites: Favorites): String{
        var str = "ID: ${favorites.favId}"
        str += '\n' + "Username: ${favorites.profileName}"
        str += '\n' + "Region: ${favorites.region}"
        return str
    }
}