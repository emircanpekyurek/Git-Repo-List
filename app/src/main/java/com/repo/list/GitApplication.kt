package com.repo.list

import android.app.Application

class GitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GitApplication
    }
}