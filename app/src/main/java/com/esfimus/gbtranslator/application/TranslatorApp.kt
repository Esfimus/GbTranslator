package com.esfimus.gbtranslator.application

import android.app.Application
import com.esfimus.gbtranslator.di.application
import com.esfimus.gbtranslator.di.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}