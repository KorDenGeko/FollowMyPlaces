package com.example.followmyplaces

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.MapStyleOptions
import retrofit2.Response

class Repository(private val apiClient:ApiClient) {
    lateinit var mMap:GoogleMap
    suspend fun getNearbyPlaces(): Response<PlacesResponse>{
        val apiInterface = apiClient.client.create(ApiInterface::class.java)
        return apiInterface.getNearbyPlaces()
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
        val success = map.setMapStyle(MapStyleOptions( context.resources.getString(R.string.style_json)))
        if (!success) {
            Log.e(context.toString(), "Style parsing failed.");
        }

    }

}