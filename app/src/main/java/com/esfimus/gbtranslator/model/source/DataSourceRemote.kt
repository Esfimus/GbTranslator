package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceRemote(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()
    ) : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}