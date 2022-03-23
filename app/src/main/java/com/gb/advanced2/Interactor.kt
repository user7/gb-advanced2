package com.gb.advanced2

import io.reactivex.Observable

interface Interactor<T> {
    // Use case: получение данных для вывода на экран
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}