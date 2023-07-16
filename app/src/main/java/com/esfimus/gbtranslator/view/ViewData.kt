package com.esfimus.gbtranslator.view

import com.esfimus.gbtranslator.model.data.AppState

interface ViewData {
    fun renderData(appState: AppState)
}