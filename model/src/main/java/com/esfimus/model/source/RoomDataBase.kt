package com.esfimus.model.source

import com.esfimus.model.data.AppState
import com.esfimus.model.dto.SearchResultDto
import com.esfimus.model.repository.convertDataModelSuccessToEntity
import com.esfimus.model.repository.mapHistoryEntityToSearchResult

class RoomDataBase(private val searchDao: com.esfimus.database.SearchDao) :
    DataSourceLocal<List<SearchResultDto>> {
    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let { searchDao.insert(it) }
    }

    override suspend fun getData(word: String): List<SearchResultDto> =
        mapHistoryEntityToSearchResult(searchDao.all())
}