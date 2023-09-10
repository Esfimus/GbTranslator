package com.esfimus.model.repository

import com.esfimus.model.data.AppState
import com.esfimus.model.dto.SearchResultDto
import com.esfimus.model.source.DataSourceLocal

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<List<SearchResultDto>>) :
    RepositoryLocal<List<SearchResultDto>> {
    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<SearchResultDto> = dataSource.getData(word)
}