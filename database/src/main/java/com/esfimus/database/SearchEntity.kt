package com.esfimus.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val word: String
)