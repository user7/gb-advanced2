package com.gb.advanced2.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Articles
import com.gb.advanced2.entities.SearchHistoryRecords
import com.gb.advanced2.externals.os.DispatcherProvider
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel(
    private val articlesModel: Contract.ArticlesModel,
    private val historyModel: Contract.HistoryModel,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel(),
    Contract.ViewModel {

    private val ioScope =
        CoroutineScope(dispatcherProvider.io())

    // экран поиска
    private val searchScreenState =
        MutableLiveData<Contract.SearchScreenState>(Contract.SearchScreenState.Empty())
    override fun getSearchScreenState(): LiveData<Contract.SearchScreenState> = searchScreenState

    private val searchErrorHandler = CoroutineExceptionHandler { _, e -> onSearchLoadingError(e) }
    override fun search(searchString: String) {
        searchScreenState.value = Contract.SearchScreenState.Loading()
        ioScope.launch(searchErrorHandler) {
            val result = articlesModel.getArticles(searchString)
            launch(dispatcherProvider.ui()) {
                onSearchDataReady(result)
            }
        }
    }

    private fun onSearchDataReady(data: Articles) {
        searchScreenState.postValue(Contract.SearchScreenState.DataLoaded(data))
    }

    private fun onSearchLoadingError(error: Throwable?) {
        Timber.d("=== error: ${error?.toString() ?: "Unknown error"}")
        searchScreenState.postValue(
            Contract.SearchScreenState.Error(
                error?.toString() ?: "Unknown Error"
            )
        )
    }

    // экран истории
    private val historyScreenState =
        MutableLiveData<Contract.HistoryScreenState>(Contract.HistoryScreenState.Loading())
    override fun getHistoryScreenState(): LiveData<Contract.HistoryScreenState> = historyScreenState

    private val historyErrorHandler = CoroutineExceptionHandler { _, e -> onHistoryLoadingError(e) }
    private fun loadHistory() {
        ioScope.launch(historyErrorHandler) {
            val result = historyModel.getHistory()
            launch(dispatcherProvider.ui()) {
                onHistoryReady(result)
            }
        }
    }

    private fun onHistoryReady(results: SearchHistoryRecords) {
        historyScreenState.postValue(Contract.HistoryScreenState.HistoryLoaded(results))
    }

    private fun onHistoryLoadingError(error: Throwable?) {
        historyScreenState.postValue(
            Contract.HistoryScreenState.Error(
                error?.toString() ?: "Unknown Error"
            )
        )
    }

    init {
        loadHistory()
    }
}