package com.esfimus.gbtranslator.view

import com.esfimus.gbtranslator.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}