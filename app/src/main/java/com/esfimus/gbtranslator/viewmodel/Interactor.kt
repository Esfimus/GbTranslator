package com.esfimus.gbtranslator.viewmodel

import com.esfimus.gbtranslator.model.data.AppState
import io.reactivex.rxjava3.core.Observable

interface Interactor<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState>
}