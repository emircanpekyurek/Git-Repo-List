package com.repo.list.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : BaseContract.View> : BaseContract.Presenter<T> {

    protected var view: T? = null
    private lateinit var disposables : CompositeDisposable

    override fun attachView(view: T) {
        this.view = view
        disposables = CompositeDisposable()
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
    override fun detachView() {
        view = null
        disposables.dispose()
    }

}