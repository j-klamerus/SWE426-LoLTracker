package com.example.loltracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.loltracker.dao.RecentDao
import com.example.loltracker.model.Recent

class RecentViewModel(private val dao: RecentDao) : ViewModel() {
    var newRecentName = ""
    var newRegion = ""
    private val recent = dao.getAll()
    val recentString: LiveData<String> = recent.map{
            recent -> formatRecent(recent)
    }

    fun addRecent(){
        viewModelScope.launch{
            val recent = Recent()
            recent.profileName = newRecentName
            recent.region = newRegion
            dao.insert(recent)
        }
    }

    fun delRecent(){
        viewModelScope.launch{
            val recent = Recent()
            dao.deleteAll()
        }
    }

    fun formatRecent(recent: List<Recent>): String{
        return recent.fold(""){
                str, item -> str + '\n' + formatRecent(item)
        }
    }

    fun formatRecent(recent: Recent): String{
        var str = "ID: ${recent.recId}"
        str += '\n' + "Username: ${recent.profileName}"
        str += '\n' + "Region: ${recent.region}"
        return str
    }
}