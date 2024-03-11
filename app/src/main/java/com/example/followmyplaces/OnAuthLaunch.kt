package com.example.followmyplaces

import android.content.Intent

interface OnAuthLaunch {
    fun launch(intent: Intent)
    fun showMapFragment()
}

interface OnAddClickListener{
    fun onFabClick()
}