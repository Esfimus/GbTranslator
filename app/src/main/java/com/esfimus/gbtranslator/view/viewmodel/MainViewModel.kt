package com.esfimus.gbtranslator.view.viewmodel

import androidx.lifecycle.LiveData
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.data.DataModel
import com.esfimus.gbtranslator.model.data.Meanings
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private val liveDataToObserve: LiveData<AppState> = liveData

    fun subscribe(): LiveData<AppState> = liveDataToObserve

    override fun getData(word: String, isOnline: Boolean) {
        liveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            liveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
        }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }

    private fun parseSearchResults(data: AppState): AppState {
        val newSearchResults = arrayListOf<DataModel>()
        when (data) {
            is AppState.Success -> {
                val searchResults = data.data
                if (!searchResults.isNullOrEmpty()) {
                    for (searchResult in searchResults) {
                        parseResult(searchResult, newSearchResults)
                    }
                }
            }
            else -> println("shit")
        }

        return AppState.Success(newSearchResults)
    }

    private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {
        if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
            val newMeanings = arrayListOf<Meanings>()
            for (meaning in dataModel.meanings) {
                if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                    newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
                }
            }
            if (newMeanings.isNotEmpty()) {
                newDataModels.add(DataModel(dataModel.text, newMeanings))
            }
        }
    }
}