package com.esfimus.gbtranslator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.rx.SchedulerProviderImpl
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T: AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl()
) : ViewModel() {

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}