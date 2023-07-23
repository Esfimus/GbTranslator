package com.esfimus.gbtranslator.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.viewmodel.BaseViewModel

abstract class BaseActivity<T: AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: AppState)
}