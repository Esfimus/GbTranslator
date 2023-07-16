package com.esfimus.gbtranslator.presenter

import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.view.ViewData

interface Presenter<T: AppState, V: ViewData> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}