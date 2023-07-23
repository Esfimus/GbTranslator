package com.esfimus.gbtranslator.viewmodel

import androidx.lifecycle.LiveData
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.repository.RepositoryImpl
import com.esfimus.gbtranslator.model.source.DataSourceLocal
import com.esfimus.gbtranslator.model.source.DataSourceRemote
import io.reactivex.rxjava3.observers.DisposableObserver

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    )
) : BaseViewModel<AppState>() {
    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveData.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(state: AppState) {
                appState = state
                liveData.value = state
            }

            override fun onError(e: Throwable) {
                liveData.value = AppState.Error(e)
            }

            override fun onComplete() { }

        }
    }
}