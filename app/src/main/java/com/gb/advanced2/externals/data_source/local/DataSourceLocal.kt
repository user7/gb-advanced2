package com.gb.advanced2.externals.data_source.local

import com.gb.advanced2.business.DataModel
import com.gb.advanced2.externals.data_source.DataSource
import com.gb.advanced2.RoomDataBaseImplementation
import io.reactivex.Observable

// Для локальных данных используется Room
class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()
) :
    DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}