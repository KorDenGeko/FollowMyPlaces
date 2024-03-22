package com.example.followmyplaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class StyleListAdapter(val context:Context, val items:List<Style>): BaseAdapter() {
    override fun getCount(): Int = items.count()

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val myConvertView: View
        if (convertView == null){
            myConvertView = LayoutInflater.from(context)
                .inflate(R.layout.style_item_layout, parent, false)
            val listTitle: TextView = myConvertView.findViewById(R.id.mapStyleText)
            val listImage: ImageView = myConvertView.findViewById(R.id.mapStyleImage)
            val holder = CustomViewHolder(listTitle, listImage)
            myConvertView.tag = holder
            listTitle.text = items[position].name
            when(items[position].photoName){
                "night"->listImage.setImageResource(R.drawable.night)
                "retro"->listImage.setImageResource(R.drawable.retro)
                "silver"->listImage.setImageResource(R.drawable.silver)
                "ultra_light"->listImage.setImageResource(R.drawable.ultra_light)
                "dark"->listImage.setImageResource(R.drawable.dark)

            }
        }
        else {
            myConvertView = convertView
            val convertViewHolder = myConvertView.tag as CustomViewHolder
            convertViewHolder.listTitle.text = items[position].name

            when (items[position].photoName) {
                "night" -> convertViewHolder.listImage.setImageResource(R.drawable.night)
                "retro_style" -> convertViewHolder.listImage.setImageResource(R.drawable.retro)
                "silver" -> convertViewHolder.listImage.setImageResource(R.drawable.silver)
                "ultra_light" -> convertViewHolder.listImage.setImageResource(R.drawable.ultra_light)
                "dark" -> convertViewHolder.listImage.setImageResource(R.drawable.dark)
            }
        }



        return myConvertView

    }
}

class CustomViewHolder(val listTitle: TextView, val listImage: ImageView)