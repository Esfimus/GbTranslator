package com.esfimus.gbtranslator.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class SearchRepository(private val searchDao: SearchDao) {

    val wordsHistory: LiveData<List<SearchEntity>> = searchDao.getSearchEntities()

    @WorkerThread
    suspend fun insertWord(entity: SearchEntity) {
        searchDao.insert(entity)
    }
}