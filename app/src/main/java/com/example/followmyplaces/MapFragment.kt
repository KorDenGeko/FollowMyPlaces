package com.example.followmyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


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
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapContainer) as SupportMapFragment
            supportMapFragment.getMapAsync {map ->
                val coordinates_Lviv = LatLng(49.842957, 24.031111)
                map.addMarker(MarkerOptions().position(coordinates_Lviv).title("Ви тут"))
                map.setMapStyle(
                    MapStyleOptions(getResources().getString(R.string.style_json)))
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates_Lviv, 10F))
            }
        }

}