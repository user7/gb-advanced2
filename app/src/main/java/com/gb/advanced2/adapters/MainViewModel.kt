package com.gb.advanced2.adapters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Articles
import kotlinx.coroutines.*

class MainViewModel(private val model: Contract.Model) : ViewModel(),
    Contract.ViewModel {

    private val mutableState = MutableLiveData<Contract.AppState>(Contract.AppState.Empty())
    override fun getState(): LiveData<Contract.AppState> = mutableState

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun search(searchString: String) {
        mutableState.value = Contract.AppState.Loading()
        ioScope.launch {
            try {
                val result = model.getArticles(searchString)
                launch(Dispatchers.Main) {
                    onDataReady(result)
                }
            } catch(e: Throwable) {
                launch(Dispatchers.Main) {
                    Log.d("===", "error: ${e.toString()}")
                    onLoadingError(e)
                }
            }
        }
    }

    private fun onLoadingError(error: Throwable?) {
        mutableState.postValue(Contract.AppState.Error(error?.toString() ?: "Unknown Error"))
    }

    private fun onDataReady(data: Articles) {
        mutableState.postValue(Contract.AppState.DataLoaded(data))
    }
}