package com.esfimus.gbtranslator.model.repository

import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.source.DataSource

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) : Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}