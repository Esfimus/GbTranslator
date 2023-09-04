package com.esfimus.gbtranslator.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.esfimus.gbtranslator.database.SearchDatabase
import com.esfimus.gbtranslator.database.SearchEntity
import com.esfimus.gbtranslator.database.SearchRepository
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    val searchHistoryLive: LiveData<List<SearchEntity>>
    private val searchRepository: SearchRepository

    init {
        val searchDao = SearchDatabase.getSearchDatabase(application).searchDao()
        searchRepository = SearchRepository(searchDao)
        searchHistoryLive = searchRepository.wordsHistory
    }

    fun addWord(word: SearchEntity) = viewModelScope.launch {
        searchRepository.insertWord(word)
    }
}