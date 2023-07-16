package com.esfimus.gbtranslator.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esfimus.gbtranslator.model.data.AppState
import com.esfimus.gbtranslator.presenter.Presenter
import com.esfimus.gbtranslator.view.ViewData

abstract class BaseActivity<T: AppState> : AppCompatActivity(), ViewData {

    protected lateinit var presenter: Presenter<T, ViewData>

    protected abstract fun createPresenter(): Presenter<T, ViewData>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}