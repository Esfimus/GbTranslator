package com.esfimus.gbtranslator.presenter

import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.model.repository.RepositoryImpl
import com.esfimus.gbtranslator.model.source.DataSourceLocal
import com.esfimus.gbtranslator.model.source.DataSourceRemote
import com.esfimus.gbtranslator.presenter.rx.SchedulerProviderImpl
import com.esfimus.gbtranslator.view.ViewData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MainPresenter<T: AppState, V: ViewData>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerProvider: SchedulerProviderImpl = SchedulerProviderImpl()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() { }
        }
    }
}