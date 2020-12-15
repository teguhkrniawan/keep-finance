package com.teguh.keepfinance.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teguh.keepfinance.R
import com.teguh.keepfinance.helper.Commons
import com.teguh.keepfinance.helper.ConvertKurs
import com.teguh.keepfinance.model.DataTransaksi

class TransaksiAdapter(
    val context: Context?,
    val transaksiList: ArrayList<DataTransaksi>?,
    val itemClick: TransaksiItemClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(): this(null, null, null)

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTanggalGroup = itemView.findViewById<TextView>(R.id.tanggal_group_item)
    }

    inner class TransaksiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNominal = itemView.findViewById<TextView>(R.id.transaksi_nominal)
        val txtKeterangan = itemView.findViewById<TextView>(R.id.transaksi_keterangan)
        val btnDelete = itemView.findViewById<ImageView>(R.id.transaksi_btn_delete)
    }

    fun resetDataTransaksi() {
        transaksiList?.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            Commons.VIEW_TYPE_GROUP -> {
                val grup = inflater.inflate(R.layout.group_item_layout, parent, false) as ViewGroup
                return GroupViewHolder(grup)
            }
            Commons.VIEW_TYPE_TRANSAKSI -> {
                val transaksi = inflater.inflate(R.layout.transaksi_item_layout, parent, false) as ViewGroup
                return TransaksiViewHolder(transaksi)
            }
            else -> {
                val grup = inflater.inflate(R.layout.group_item_layout, parent, false) as ViewGroup
                return GroupViewHolder(grup)
            }
        }
    }

    override fun getItemCount(): Int {
        return transaksiList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return transaksiList?.get(position)?.viewType!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemData = transaksiList?.get(position)

        if (holder is GroupViewHolder){
            holder.txtTanggalGroup.text = itemData?.tanggalTransaksi
        } else if (holder is TransaksiViewHolder) {
            holder.txtNominal.text = ConvertKurs.formatRupiah(itemData?.nominalTransaksi?.toInt() ?: 0)
            if (itemData?.jenis == "income")
                holder.txtNominal.setTextColor(Color.parseColor("#008000"))
            else
                holder.txtNominal.setTextColor(Color.RED)
            holder.txtKeterangan.text = itemData?.keteranganTransaksi
            holder.itemView.setOnClickListener {
                itemClick?.detail(itemData)
            }
            holder.btnDelete.setOnClickListener {
                itemClick?.hapus(itemData)
            }
        }
    }
}

interface TransaksiItemClickListener {
    fun detail (item:DataTransaksi?)
    fun hapus (item: DataTransaksi?)
}
