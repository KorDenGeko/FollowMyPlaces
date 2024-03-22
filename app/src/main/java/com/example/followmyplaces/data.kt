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

data class Poi(val id:Int,val name: String, val nameApi:String)
object Coordinates{
    private var latLng = LatLng(50.4546600, 30.5238000)
    private var placeType:Poi = Poi(0,"","")
    fun setLatLng(placesLatLng: LatLng){
        latLng = placesLatLng
    }

    fun getLatLng() = latLng

    fun setPlaceType(selectedType:Poi){
        placeType = selectedType
    }

    fun getPlaceType() = placeType
}

object PoiTypes{
    val poiList = listOf(
        Poi(1,"Туристичні місця", "tourist_attractions"),
        Poi(2,"Аптеки","pharmacy"),
        Poi(3,"Музеї","museum"),
        Poi(4,"Художні галереї","art_gallery"),
        Poi(5,"Парки розваг","amusement_park"),
        Poi(6,"Торгівельні центри","shopping_mall"),
        Poi(7,"Станції метро","subway_station")
    )
}