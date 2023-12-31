package com.esfimus.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchEntity::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun historyDao(): SearchDao
}