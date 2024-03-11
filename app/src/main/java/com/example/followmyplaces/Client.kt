package com.example.followmyplaces


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Client {
    val client = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface ApiInterface {
    @GET("/maps/api/directions/json?&origin=49.842957,24.031111&destination=49.553516,25.594767&key=AIzaSyCeah-PDQx4eIob_HtYO6mvTsoeW9JIoUg")
    suspend fun getSimpleRoute(): Response<DirectionsResponse>
    @GET("/maps/api/place/nearbysearch/json?location=49.842957,24.031111&radius=2000&type=tourist_attractions&key=AIzaSyCeah-PDQx4eIob_HtYO6mvTsoeW9JIoUg")
    suspend fun getNearbyPlaces(): Response<PlacesResponse>
    @GET("/maps/api/directions/json?")
    suspend fun getComplexRoute(
        @Query("origin") originId: String,
        @Query("destination") destinationId: String,
        @Query("waypoints") waypoints: String,
        @Query("key") key: String = "AIzaSyCeah-PDQx4eIob_HtYO6mvTsoeW9JIoUg"
    ): Response<DirectionsResponse>
}