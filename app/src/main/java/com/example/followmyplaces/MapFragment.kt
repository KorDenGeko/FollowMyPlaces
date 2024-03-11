package com.example.followmyplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MapFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        supportMapFragment.getMapAsync {map ->
                val coordinatesKiyv = LatLng(50.4546600, 30.5238000)
                map.addMarker(MarkerOptions().position(coordinatesKiyv).title("Ви тут"))
                val success = map.setMapStyle(MapStyleOptions(resources.getString(R.string.style_json)))
                if (!success) {
                    Log.e(tag, "Style parsing failed.");
                }
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinatesKiyv, 10F))
            CoroutineScope(Dispatchers.IO).launch {
                val result = Client.client.create(ApiInterface::class.java).getNearbyPlaces()
                if (result.isSuccessful) {
                    var locations = mutableListOf<Location>()
                    result.body()?.let {
                        it.results.forEach { result ->
                            val location = Location(result.geometry.location.lat,result.geometry.location.lng,result.name)
                            locations.add(location)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        locations.forEach {
                            val coordinates = LatLng(it.lat, it.lng)
                            map.addMarker(MarkerOptions().position(coordinates).title(it.name))
                        }
                    }
                }
                withContext(Dispatchers.IO) {
                    val placeCoordinates = mutableListOf<String>()
                    result.body()?.let { result ->
                        result.results.forEach {
                            placeCoordinates.add("${it.geometry.location.lat},${it.geometry.location.lng}")
                        }
                    }
                    val waypointCoordinates = placeCoordinates.drop(0).take(10)
                    val waypointCoordinatesString =
                        waypointCoordinates.joinToString(separator = "|")
                    val routeResult = Client.client.create(ApiInterface::class.java)
                        .getComplexRoute(
                            originId = placeCoordinates[0],
                            destinationId = placeCoordinates.last(),
                            waypoints = waypointCoordinatesString
                        )
                    if (routeResult.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            routeResult.body()?.let {
                                if(it.routes.isNotEmpty()) {
                                    val polylinePoints = it.routes[0].overviewPolyline.points
                                    val decodedPath = PolyUtil.decode(polylinePoints)
                                    map.addPolyline(PolylineOptions().addAll(decodedPath))

                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

