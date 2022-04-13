package com.gb.advanced2.app

import com.gb.advanced2.adapters.MainViewModel
import com.gb.advanced2.externals.os.DispatcherProvider
import com.gb.advanced2.externals.repo.articles.RemoteRepository
import com.gb.advanced2.externals.repo.history.LocalRepository
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin

val koinModule = module {
    scope(named("scope_main_activity")) {
        scoped { NavigatorHolder() }
    }
    single<Contract.ArticlesModel> { RemoteRepository() }
    single<Contract.HistoryModel> { LocalRepository(context = androidContext()) }
    single { DispatcherProvider() }
    single<Contract.ViewModel> {
        MainViewModel(
            articlesModel = get(),
            historyModel = get(),
            dispatcherProvider = get(),
        )
    }
}

const val MAIN_SCOPE_TAG = "scope_main_activity"
const val MAIN_SCOPE_ID = "main_activity"
fun createMainScope(): Scope = getKoin().createScope(MAIN_SCOPE_ID, named(MAIN_SCOPE_TAG))
fun getMainScope(): Scope = getKoin().getScope(MAIN_SCOPE_ID)