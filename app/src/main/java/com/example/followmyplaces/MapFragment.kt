package com.example.followmyplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MapFragment:Fragment() {
    val placesMap:MutableMap<LatLng,Results> = mutableMapOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
        supportMapFragment.getMapAsync { map ->
            map.addMarker(MarkerOptions().position(CoordinatesKyiv.latLng).title("Ви тут"))
            viewModel.setMapStyle(view.context, map)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(CoordinatesKyiv.latLng, 10F))
            viewModel.getNearbyPlaces()
            viewModel.uiState.observe(this.viewLifecycleOwner) {
                when (it) {
                    MapViewModel.UIState.Empty -> Unit
                    is MapViewModel.UIState.Error -> Toast.makeText(
                        view.context,
                        "${it.description}",
                        Toast.LENGTH_SHORT
                    ).show()

                    MapViewModel.UIState.Processing -> Toast.makeText(
                        view.context,
                        "Завантажуємо данні...",
                        Toast.LENGTH_SHORT
                    ).show()

                    is MapViewModel.UIState.Result -> {
                        if (it.placesResponse.results.isNotEmpty()) {
                            var locations = mutableListOf<Location>()
                            it.placesResponse.results.let {
                                it.forEach { result ->
                                    val location = Location(
                                        result.geometry.location.lat,
                                        result.geometry.location.lng,
                                        result.name
                                    )
                                    locations.add(location)
                                    placesMap.put(
                                        LatLng(
                                            result.geometry.location.lat,
                                            result.geometry.location.lng
                                        ), result
                                    )
                                    locations.forEach {
                                        val coordinates = LatLng(it.lat, it.lng)
                                        map.addMarker(
                                            MarkerOptions().position(coordinates).title(it.name)
                                        )
                                        map.setOnMarkerClickListener(object :
                                            GoogleMap.OnMarkerClickListener {
                                            override fun onMarkerClick(marker: Marker): Boolean {
                                                showDescription(placesMap.get(marker.position))
                                                return false
                                            }
                                        })
                                    }


                                }
                            }

                            val placeCoordinates = mutableListOf<String>()
                            it.placesResponse.results.let { result ->
                                result.forEach {
                                    placeCoordinates.add("${it.geometry.location.lat},${it.geometry.location.lng}")
                                    val waypointCoordinates = placeCoordinates.drop(0).take(10)
                                    val waypointCoordinatesString =
                                        waypointCoordinates.joinToString(separator = "|")
                                    viewModel.getComplexRoute(
                                        originId = placeCoordinates[0],
                                        destinationId = placeCoordinates.last(),
                                        waypoints = waypointCoordinatesString
                                    )
                                }
                            }
                        }
                    }

                    is MapViewModel.UIState.ResultDirections -> {
                        if (it.directionsResponse.routes.isNotEmpty()) {
                            val polylinePoints =
                                it.directionsResponse.routes[0].overviewPolyline.points
                            val decodedPath = PolyUtil.decode(polylinePoints)
                            map.addPolyline(PolylineOptions().addAll(decodedPath))
                        }
                    }
                }

            }
        }



    }

    fun showDescription(marker: Results?) {
        val detailsFragmentToAdd = PhotoFragment()
        detailsFragmentToAdd.setMarker(marker!!)
        parentFragmentManager.beginTransaction()
            .add(R.id.container, detailsFragmentToAdd)
            .addToBackStack("details_fragment")
            .commit()
    }

}



