package com.example.followmyplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PhotoFragment:Fragment() {
    private var photos:List<Photos>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_fragment_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val myAdapter = RecyclerViewAdapter(photos as MutableList<Photos>)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    fun setPhotos(photosSet: List<Photos>) {
        photos = photosSet
    }
}