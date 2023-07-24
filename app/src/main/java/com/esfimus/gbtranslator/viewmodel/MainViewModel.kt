package com.esfimus.gbtranslator.viewmodel

import androidx.lifecycle.LiveData
import com.esfimus.gbtranslator.model.data.AppState
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> = liveData

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveData.value = AppState.Loading(null) }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
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