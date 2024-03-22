package com.example.followmyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainFragment:DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!Places.isInitialized()){
            Places.initialize(view.context,"AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU")
        }
        val adapter = MySpinnerAdapter(view.context,PoiTypes)
        val greatingText:TextView = view.findViewById(R.id.greatingText)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
            spinner.adapter = adapter

            greatingText.text = "Привіт, ${GoogleServices.getAccount(requireContext())?.givenName}!"

        val autoCompleteSupFrag = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autoCompleteSupFrag.setPlaceFields(listOf(Place.Field.LAT_LNG,Place.Field.NAME))

        autoCompleteSupFrag.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onError(p0: Status) {
                Toast.makeText(view.context, "Щось пішло не так.Спробуйте ще", Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceSelected(p0: Place) {
                val latLng = p0.latLng
                if (latLng != null){
                Coordinates.setLatLng(latLng)
                }
            }
        })

        val button:Button = view.findViewById(R.id.mapButton)
        button.setOnClickListener {
            Coordinates.setPlaceType(spinner.selectedItem as Poi)
            parentFragmentManager.beginTransaction()
                .add(R.id.container, MapFragment())
                .addToBackStack("MapFragment")
                .commit()
        }

    }

}