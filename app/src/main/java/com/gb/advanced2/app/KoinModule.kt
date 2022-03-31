package com.gb.advanced2.app

import com.gb.advanced2.adapters.MainViewModel
import com.gb.advanced2.externals.repo.RemoteRepository
import org.koin.dsl.module

val koinModule = module {
    single<Contract.Model> { RemoteRepository() }
    single<Contract.ViewModel> { MainViewModel(get()) }
}
