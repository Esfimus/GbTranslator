package com.esfimus.gbtranslator.model.source

import com.esfimus.gbtranslator.model.data.DataModel

class RoomDataBase : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }
}