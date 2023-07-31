package com.esfimus.gbtranslator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esfimus.gbtranslator.model.data.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel<T: AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
        + SupervisorJob()
        + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)
}