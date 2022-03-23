package com.gb.advanced2.externals.data_source

import com.gb.advanced2.business.DataModel
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    // Репозиторий возвращает данные, используя dataSource (локальный или внешний)
    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}