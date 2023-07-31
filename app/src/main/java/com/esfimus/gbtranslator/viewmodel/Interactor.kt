package com.esfimus.gbtranslator.viewmodel

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}