package com.esfimus.gbtranslator.di

import com.esfimus.gbtranslator.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ModuleActivity {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}