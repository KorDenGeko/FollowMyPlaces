package com.example.followmyplaces

import android.app.Application


class MyApplication: Application() {
    lateinit var repo:Repository
    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = Repository(ApiClient())
    }
    companion object {
        private lateinit var instance:MyApplication
        fun getApp() = instance
    }
}