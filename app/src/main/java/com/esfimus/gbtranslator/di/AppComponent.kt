package com.esfimus.gbtranslator.di

import android.app.Application
import com.esfimus.gbtranslator.application.TranslatorApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ModuleActivity::class,
        ModuleInteractor::class,
        ModuleRepository::class,
        ModuleViewModel::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: TranslatorApp)
}