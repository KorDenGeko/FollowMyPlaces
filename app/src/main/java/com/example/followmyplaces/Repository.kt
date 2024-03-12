package com.example.followmyplaces

import retrofit2.Response

class Repository(private val apiClient:ApiClient) {
    suspend fun getNearbyPlaces(): Response<PlacesResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getNearbyPlaces()
    }

    suspend fun getSimpleRoute(): Response<DirectionsResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getSimpleRoute()
    }

    suspend fun getComplexRoute(originId: String,destinationId: String,waypoints: String, key: String ):Response<DirectionsResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getComplexRoute(originId,destinationId,waypoints, key)
    }
}