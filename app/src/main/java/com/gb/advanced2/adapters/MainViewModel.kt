package com.gb.advanced2.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Article
import com.gb.advanced2.entities.SearchHistoryRecord
import com.gb.advanced2.externals.os.DispatcherProvider
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel(
    private val articlesModel: Contract.ArticlesModel,
    private val historyModel: Contract.HistoryModel,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel(),
    Contract.ViewModel {

    private val ioScope = CoroutineScope(dispatcherProvider.io())
    private var job: Job? = null

    // экран поиска
    private val searchScreenState =
        MutableLiveData<Contract.SearchScreenState>(Contract.SearchScreenState.Empty())

    override fun getSearchScreenState(): LiveData<Contract.SearchScreenState> = searchScreenState

    private val searchErrorHandler = CoroutineExceptionHandler { _, e -> onSearchLoadingError(e) }
    override fun search(searchString: String) {
        searchScreenState.value = Contract.SearchScreenState.Loading()
        job?.cancel()
        job = ioScope.launch(searchErrorHandler) {
            val result = articlesModel.getArticles(searchString)
            launch {
                historyModel.saveHistoryRecord(
                    SearchHistoryRecord(
                        searchQuery = searchString,
                        resultsCount = result.size,
                    )
                )
                suspendLoadHistory()
            }
            searchScreenState.postValue(Contract.SearchScreenState.DataLoaded(result))
        }
    }

    private fun onSearchLoadingError(error: Throwable?) {
        val msg = error?.toString() ?: "Unknown error"
        Timber.d("!! search error: $msg")
        searchScreenState.postValue(Contract.SearchScreenState.Error(msg))
    }

    // экран истории
    private val historyScreenState =
        MutableLiveData<Contract.HistoryScreenState>(Contract.HistoryScreenState.Loading())

    override fun getHistoryScreenState(): LiveData<Contract.HistoryScreenState> = historyScreenState

    private val historyErrorHandler = CoroutineExceptionHandler { _, e -> onHistoryLoadingError(e) }
    private fun loadHistory() {
        job?.cancel()
        job = ioScope.launch(historyErrorHandler) { suspendLoadHistory() }
    }

    private suspend fun suspendLoadHistory() {
        val history = historyModel.loadHistory()
        historyScreenState.postValue(Contract.HistoryScreenState.HistoryLoaded(history))
    }

    private fun onHistoryLoadingError(error: Throwable?) {
        val msg = error?.toString() ?: "Unknown error"
        Timber.d("!! db error: $msg")
        historyScreenState.postValue(Contract.HistoryScreenState.Error(msg))
    }

    init {
        loadHistory()
    }

    private val descriptionScreenState = MutableLiveData<Article>(null)
    override fun getDescriptionScreenState(): LiveData<Article> = descriptionScreenState
    override fun setDescriptionArticle(article: Article) = descriptionScreenState.postValue(article)
}