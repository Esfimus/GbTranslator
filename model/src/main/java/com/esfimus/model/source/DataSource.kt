package com.esfimus.gbtranslator.model.source

interface DataSource<T> {
    suspend fun getData(word: String): T
}