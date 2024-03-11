package com.example.followmyplaces

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.TAG
import com.google.android.gms.maps.CameraUpdateFactory
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
                    val result =  Client.client.create(ApiInterface::class.java).getSimpleRoute()
                    if (result.isSuccessful){
                        withContext(Dispatchers.Main){
                            result.body()?.let {
                                if(it.routes.isNotEmpty()) {
                                    val polylinePoints = it.routes[0].over .points
                                    val decodedPoints = PolyUtil.decode(polylinePoints)
                                    map.addPolyline(PolylineOptions().addAll(decodedPoints))
                                }
                            }
                        }
                    }
                }
        }

    }

}

