package com.example.followmyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
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
        val viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        val detailsName = view.findViewById<TextView>(R.id.detailsName)
        val detailsImage = view.findViewById<ImageView>(R.id.detailsImage)
        val detailsRating = view.findViewById<TextView>(R.id.detailsRating)
        detailsName.text = marker?.name
        detailsRating.text = marker?.rating
        if(marker != null) {
            if(marker!!.photos.isNotEmpty()) {
                Glide.with(view.context)
                    .load(viewModel.getRequest(marker!!.photos[0].photoReference))
                    .into(detailsImage)
            }
        }
    }

    fun setMarker(markerToSet:Results) {
        marker = markerToSet
    }
}