package com.esfimus.model.source

interface DataSource<T> {
    suspend fun getData(word: String): T
}