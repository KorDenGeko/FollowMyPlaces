package com.example.followmyplaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RecyclerViewAdapter(val items:List<Photos>):RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo_layout, parent, false)
        return RecyclerViewHolder(listItemView)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val reference = items[position].photoReference
        val request =   "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photo_reference=$reference&key=AIzaSyAbZFMlKGcXK9E1yn_BzwPh-m2Kdle3Ky4"
        Glide.with(holder.itemView.context)
            .load(items[position].photoReference)
            .into(holder.image)
        }
}





class RecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    val image: ImageView = itemView.findViewById(R.id.photoImage)
}