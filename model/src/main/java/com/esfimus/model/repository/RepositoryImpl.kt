package com.esfimus.model.repository

import com.esfimus.model.dto.SearchResultDto
import com.esfimus.model.source.DataSource

class RepositoryImpl(private val dataSource: DataSource<List<SearchResultDto>>) :
    Repository<List<SearchResultDto>> {
    override suspend fun getData(word: String): List<SearchResultDto> = dataSource.getData(word)
}