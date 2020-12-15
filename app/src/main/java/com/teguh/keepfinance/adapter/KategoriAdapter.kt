package com.teguh.keepfinance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teguh.keepfinance.R
import com.teguh.keepfinance.model.DataKategori

class KategoriAdapter(
    val listKategori: ArrayList<DataKategori>?,
    val itemClick: KategoriItemClickListener
) : RecyclerView.Adapter<KategoriAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtNameCategory = itemView.findViewById<TextView>(R.id.categories_item_name)
        val ivHapus = itemView.findViewById<ImageView>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categories_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return listKategori?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val item = listKategori?.get(position)
       holder.txtNameCategory.text = item?.namaKategori
       holder.itemView.setOnClickListener {
           itemClick.detailItem(item)
       }
       holder.ivHapus.setOnClickListener {
            itemClick.hapusItem(item)
       }
    }

}

interface KategoriItemClickListener {
    fun detailItem(item: DataKategori?)
    fun hapusItem(item: DataKategori?)
}