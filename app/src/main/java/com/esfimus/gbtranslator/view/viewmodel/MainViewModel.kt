package com.esfimus.gbtranslator.view.viewmodel

import androidx.lifecycle.LiveData
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.model.data.AppState
import com.esfimus.model.repository.parseOnlineSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private val liveDataToObserve: LiveData<AppState> = liveData

    fun subscribe(): LiveData<AppState> = liveDataToObserve

    override fun getData(word: String, isOnline: Boolean) {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            liveData.postValue(parseOnlineSearchResults(interactor.getData(word, isOnline)))
        }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}