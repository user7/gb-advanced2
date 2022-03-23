package com.gb.advanced2.externals.data_source.remote

import com.gb.advanced2.business.DataModel
import com.gb.advanced2.externals.data_source.DataSource
import com.gb.advanced2.externals.data_source.remote.impl.RetrofitImplementation
import io.reactivex.Observable

// Для получения внешних данных мы будем использовать Retrofit
class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}