package com.repo.list.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object Request {

    fun <T> disposable(observable: Observable<Response<T>>, apiResponse: ApiResponse<T>): Disposable =
        observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        apiResponse.onSuccessResponse(response.body()!!)
                    } else {
                        apiResponse.onFailResponse("null")
                    }
                } else {
                    apiResponse.onFailResponse(response.message())
                }
            }, { t: Throwable? ->
                t?.let {
                    apiResponse.onFailResponse("${it.message}")
                } ?: run {
                    apiResponse.onFailResponse("null message")
                }
            })

}