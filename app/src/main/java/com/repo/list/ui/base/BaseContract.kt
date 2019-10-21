package com.repo.list.ui.base

import io.reactivex.disposables.Disposable

class BaseContract {

    interface View {
        fun onDatabaseError()
        fun onError(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter<T : View> {
        fun attachView(view: T)
        fun addDisposable(disposable: Disposable)
        fun detachView()
    }
}