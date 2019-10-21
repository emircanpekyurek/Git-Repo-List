package com.repo.list.network

interface ApiResponse<T> {
    fun onSuccessResponse(response: T)
    fun onFailResponse(errorMessage: String)
}