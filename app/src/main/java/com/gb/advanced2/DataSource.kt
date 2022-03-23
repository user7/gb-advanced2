package com.gb.advanced2

import io.reactivex.Observable

interface DataSource<T> {
    fun getData(word: String): Observable<T>
}