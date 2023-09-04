package com.esfimus.gbtranslator.di

import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.repository.Repository
import com.esfimus.gbtranslator.model.repository.RepositoryImpl
import com.esfimus.gbtranslator.model.source.RetrofitImpl
import com.esfimus.gbtranslator.model.source.RoomDataBase
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.gbtranslator.view.viewmodel.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(RoomDataBase()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}