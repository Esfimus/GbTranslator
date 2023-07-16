package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(
    private val remoteProvider: RoomDataBase = RoomDataBase()
) : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}