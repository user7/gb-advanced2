package com.gb.advanced2.adapters

interface Presenter<T : AppState, V : AppView> {
    fun attachView(view: V)
    fun detachView(view: V)

    // Получение данных с флагом isOnline (из Интернета или нет)
    fun getData(word: String, isOnline: Boolean)
}