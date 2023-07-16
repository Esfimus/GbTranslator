package com.esfimus.gbtranslator.model.repository

import com.esfimus.gbtranslator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

interface Repository<T> {
    fun getData(word: String): Observable<List<DataModel>>
}