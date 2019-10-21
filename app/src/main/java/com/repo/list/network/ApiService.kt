package com.repo.list.network

import com.repo.list.model.response.GithubUserRepos
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{user}/repos")
    fun getData(@Path("user") user: String): Observable<Response<MutableList<GithubUserRepos>>>


}