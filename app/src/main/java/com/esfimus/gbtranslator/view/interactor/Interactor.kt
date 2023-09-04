package com.esfimus.gbtranslator.view.interactor

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}