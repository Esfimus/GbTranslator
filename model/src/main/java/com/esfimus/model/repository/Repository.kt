package com.esfimus.gbtranslator.model.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}