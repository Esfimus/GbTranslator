package com.esfimus.gbtranslator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {
    @Query("SELECT * FROM SearchEntity ORDER BY id DESC")
    fun getSearchEntities(): LiveData<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SearchEntity)
}