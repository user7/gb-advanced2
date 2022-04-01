package com.gb.advanced2.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Articles
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel(private val model: Contract.Model) : ViewModel(),
    Contract.ViewModel {

    private val mutableState = MutableLiveData<Contract.AppState>(Contract.AppState.Empty())
    override fun getState(): LiveData<Contract.AppState> = mutableState

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, e -> onLoadingError(e) }

    override fun search(searchString: String) {
        mutableState.value = Contract.AppState.Loading()
        ioScope.launch(exceptionHandler) {
            val result = model.getArticles(searchString)
            launch(Dispatchers.Main) {
                onDataReady(result)
            }
        }
    }

    private fun onLoadingError(error: Throwable?) {
        Timber.d("=== error: ${error?.toString() ?: "Unknown error"}")
        mutableState.postValue(Contract.AppState.Error(error?.toString() ?: "Unknown Error"))
    }

    private fun onDataReady(data: Articles) {
        mutableState.postValue(Contract.AppState.DataLoaded(data))
    }
}