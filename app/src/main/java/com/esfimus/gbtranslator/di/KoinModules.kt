package com.esfimus.gbtranslator.di

import androidx.room.Room
import com.esfimus.gbtranslator.view.activity.MainActivity
import com.esfimus.gbtranslator.view.activity.SearchHistoryActivity
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.gbtranslator.view.interactor.SearchHistoryInteractor
import com.esfimus.gbtranslator.view.viewmodel.MainViewModel
import com.esfimus.gbtranslator.view.viewmodel.SearchHistoryViewModel
import com.esfimus.model.dto.SearchResultDto
import com.esfimus.model.repository.Repository
import com.esfimus.model.repository.RepositoryImpl
import com.esfimus.model.repository.RepositoryLocal
import com.esfimus.model.repository.RepositoryLocalImpl
import com.esfimus.model.source.RetrofitImpl
import com.esfimus.model.source.RoomDataBase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), com.esfimus.database.SearchDatabase::class.java, "search database").build() }
    single { get<com.esfimus.database.SearchDatabase>().historyDao() }

    single<Repository<List<SearchResultDto>>> { RepositoryImpl(RetrofitImpl()) }
    single<RepositoryLocal<List<SearchResultDto>>> { RepositoryLocalImpl(RoomDataBase(get())) }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val searchHistoryScreen = module {
    scope(named<SearchHistoryActivity>()) {
        scoped { SearchHistoryInteractor(get(), get()) }
        viewModel { SearchHistoryViewModel(get()) }
    }
}