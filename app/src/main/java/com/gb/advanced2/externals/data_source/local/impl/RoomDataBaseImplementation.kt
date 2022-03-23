package com.gb.advanced2

import com.gb.advanced2.business.DataModel
import com.gb.advanced2.externals.data_source.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}