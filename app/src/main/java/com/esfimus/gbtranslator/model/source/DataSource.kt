package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

interface DataSource<T> {
    fun getData(word: String): Observable<List<DataModel>>
}