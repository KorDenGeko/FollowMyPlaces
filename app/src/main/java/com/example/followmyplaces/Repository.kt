package com.example.followmyplaces

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import retrofit2.Response

class Repository(private val apiClient:ApiClient) {
    lateinit var mMap:GoogleMap
    suspend fun getNearbyPlaces(location: LatLng, radius:String, placeType:String): Response<PlacesResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getNearbyPlaces("${location.latitude},${location.longitude}",radius,placeType)
    }

    suspend fun getSimpleRoute(): Response<DirectionsResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getSimpleRoute()
    }

    suspend fun getComplexRoute(originId: String,destinationId: String,waypoints: String ):Response<DirectionsResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getComplexRoute(originId,destinationId,waypoints)
    }




    fun setMapStyle(context: Context, map: GoogleMap){
        var mapStringId = 0
        when(mapStyle.nameString){
            "night" -> mapStringId = R.string.night
            "retro_style" -> mapStringId = R.string.retro_style
            "silver" -> mapStringId = R.string.silver
            "ultra_light" -> mapStringId = R.string.ultra_light
            "dark" -> mapStringId = R.string.dark
        }
        val success = map.setMapStyle(MapStyleOptions( context.resources.getString(mapStringId)))
        if (!success) {
            Log.e(context.toString(), "Style parsing failed.");
        }

    }

    fun getRequest(reference:String?) = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photo_reference=$reference&key=AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU"



}