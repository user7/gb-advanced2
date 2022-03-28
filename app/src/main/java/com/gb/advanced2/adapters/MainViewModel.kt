package com.gb.advanced2.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.advanced2.app.App
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.Articles
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel(), Contract.ViewModel {
    private val model: Contract.Model = App.instance!!.model

    private val mutableState = MutableLiveData<Contract.AppState>(Contract.AppState.Empty)
    override fun getState(): LiveData<Contract.AppState> = mutableState

    private var disposable: Disposable? = null

    override fun search(searchString: String) {
        mutableState.value = Contract.AppState.Loading
        disposable = model.getArticles(searchString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { onLoadingError(it) }
            .subscribe { onDataReady(it) }
    }

    private fun onLoadingError(error: Throwable?) {
        mutableState.postValue(Contract.AppState.Error(error?.toString() ?: "Unknown Error"))
    }

    private fun onDataReady(data: Articles) {
        mutableState.postValue(Contract.AppState.DataLoaded(data))
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}