package com.gb.advanced2.externals.data_source

import io.reactivex.Observable

interface Repository<T> {
    fun getData(word: String): Observable<T>
}