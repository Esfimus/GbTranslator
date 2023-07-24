package com.esfimus.gbtranslator.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.viewmodel.BaseViewModel
import com.esfimus.gbtranslator.viewmodel.Interactor

abstract class BaseActivity<T: AppState, I: Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: AppState)
}