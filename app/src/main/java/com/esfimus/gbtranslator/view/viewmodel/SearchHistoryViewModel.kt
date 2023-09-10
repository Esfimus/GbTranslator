package com.esfimus.gbtranslator.view.viewmodel

import androidx.lifecycle.LiveData
import com.esfimus.gbtranslator.view.interactor.SearchHistoryInteractor
import com.esfimus.model.data.AppState
import com.esfimus.model.repository.parseLocalSearchResults
import kotlinx.coroutines.launch

class SearchHistoryViewModel(private val interactor: SearchHistoryInteractor) : BaseViewModel<AppState>() {

    private val liveDataToObserve: LiveData<AppState> = liveData

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }

    fun subscribe(): LiveData<AppState> = liveDataToObserve

    override fun getData(word: String, isOnline: Boolean) {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        liveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }
}