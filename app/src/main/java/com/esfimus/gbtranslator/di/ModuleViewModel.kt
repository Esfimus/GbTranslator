package com.esfimus.gbtranslator.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esfimus.gbtranslator.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ModuleInteractor::class])
internal abstract class ModuleViewModel {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}