package com.example.followmyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoFragment: DialogFragment() {
    private var marker:Results? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_fragment_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailsName = view.findViewById<TextView>(R.id.detailsName)
        val detailsImage = view.findViewById<ImageView>(R.id.detailsImage)
        val detailsRating = view.findViewById<TextView>(R.id.detailsRating)
        detailsName.text = marker?.name
        detailsRating.text = marker?.rating
        if(marker != null) {
            if(marker!!.photos.isNotEmpty()) {
                val reference = marker!!.photos[0].photoReference
                val request =
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photo_reference=$reference&key=AIzaSyBz-BjEp4sv7-q6C2RqH29xAHr0ConQUjU"
                Glide.with(view.context)
                    .load(request)
                    .into(detailsImage)
            }
        }
    }

    fun setMarker(markerToSet:Results) {
        marker = markerToSet
    }
}