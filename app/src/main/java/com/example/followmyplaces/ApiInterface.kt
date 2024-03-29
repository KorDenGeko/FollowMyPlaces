package com.example.followmyplaces

import com.google.android.gms.maps.model.LatLng
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/maps/api/directions/json?&origin=50.4546600,30.5238000&destination=49.553516,25.594767&key=AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU")
    suspend fun getSimpleRoute(): Response<DirectionsResponse>
    @GET("/maps/api/place/nearbysearch/json?")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") placeType: String,
        @Query("key") key: String = "AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU"
    ): Response<PlacesResponse>
    @GET("/maps/api/directions/json?")
    suspend fun getComplexRoute(
        @Query("origin") originId: String,
        @Query("destination") destinationId: String,
        @Query("waypoints") waypoints: String,
        @Query("key") key: String = "AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU"
    ): Response<DirectionsResponse>
}