package com.gb.advanced2

import io.reactivex.Observable

interface Repository<T> {
    fun getData(word: String): Observable<T>
}