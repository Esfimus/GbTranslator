package com.esfimus.gbtranslator.di

import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.repository.Repository
import com.esfimus.gbtranslator.model.repository.RepositoryImpl
import com.esfimus.gbtranslator.model.source.DataSource
import com.esfimus.gbtranslator.model.source.RetrofitImpl
import com.esfimus.gbtranslator.model.source.RoomDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleRepository {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>):
        Repository<List<DataModel>> = RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>):
            Repository<List<DataModel>> = RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBase()
}