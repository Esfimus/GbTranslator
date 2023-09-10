package com.esfimus.gbtranslator.application

import android.app.Application
import com.esfimus.gbtranslator.di.application
import com.esfimus.gbtranslator.di.mainScreen
import com.esfimus.gbtranslator.di.searchHistoryScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, searchHistoryScreen))
        }
    }
}