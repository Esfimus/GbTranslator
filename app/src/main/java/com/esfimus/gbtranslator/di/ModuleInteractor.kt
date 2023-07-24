package com.esfimus.gbtranslator.di

import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.repository.Repository
import com.esfimus.gbtranslator.viewmodel.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ModuleInteractor {
    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}