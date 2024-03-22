package com.example.followmyplaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MySpinnerAdapter(val context:Context, private val poiItem:PoiTypes):BaseAdapter() {
    override fun getCount(): Int = poiItem.poiList.count()
    override fun getItem(position: Int) = poiItem.poiList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val spinnerConvertView:View = convertView?:LayoutInflater.from(context)
            .inflate(R.layout.spinner_item_layout,parent,false)
        val poiTitle:TextView = spinnerConvertView.findViewById(R.id.spinnerText)
        poiTitle.text = poiItem.poiList[position].name
        return spinnerConvertView
    }


}