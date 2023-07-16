package com.esfimus.gbtranslator.model.repository

import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.source.DataSource
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) : Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}