package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RoomDataBase : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }
}