package com.gb.advanced2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : AppState> : AppCompatActivity(), AppView {
    protected lateinit var presenter: Presenter<T, AppView>
    protected abstract fun createPresenter(): Presenter<T, AppView>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this);
    }
}