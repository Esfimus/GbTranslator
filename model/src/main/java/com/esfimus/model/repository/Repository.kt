package com.esfimus.model.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}