package com.example.followmyplaces

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class DirectionsResponse(val routes:List<Routes>)
data class Routes(@SerializedName("overview_polyline") val overviewPolyline:OverviewPolyline)
data class OverviewPolyline(val points:String)

data class PlacesResponse(val results:List<Results>)
data class Results(val geometry:Geometry, val photos:List<Photos>, val name:String, val rating:String)
data class Geometry(val location:Location)
data class Location(val lat:Double, val lng:Double, val name:String)
data class Photos (@SerializedName("photo_reference") val photoReference:String? = null)

object CoordinatesKyiv{
    val latLng = LatLng(50.4546600, 30.5238000)
}