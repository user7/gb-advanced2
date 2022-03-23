package com.gb.advanced2.adapters

interface AppView {
    // View имеет только один метод, в который приходит некое состояние приложения
    fun renderData(appState: AppState)
}