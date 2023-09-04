package com.esfimus.gbtranslator.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    val searchHistoryLive: LiveData<List<com.esfimus.database.SearchEntity>>
    private val searchRepository: com.esfimus.database.SearchRepository

    init {
        val searchDao = com.esfimus.database.SearchDatabase.getSearchDatabase(application).searchDao()
        searchRepository = com.esfimus.database.SearchRepository(searchDao)
        searchHistoryLive = searchRepository.wordsHistory
    }

    fun addWord(word: com.esfimus.database.SearchEntity) = viewModelScope.launch {
        searchRepository.insertWord(word)
    }
}