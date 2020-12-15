package com.teguh.keepfinance.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teguh.keepfinance.R
import com.teguh.keepfinance.helper.ConvertKurs
import com.teguh.keepfinance.model.DataIncomeByCategories
import kotlin.collections.ArrayList

class IncomeCategoriesAdapter(val listIncomeByCategories : ArrayList<DataIncomeByCategories>?): RecyclerView.Adapter<IncomeCategoriesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtIncomeNameCategory = itemView.findViewById<TextView>(R.id.income_nama_kategori)
        val txtIncomeNominal = itemView.findViewById<TextView>(R.id.income_nominal_ket)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncomeCategoriesAdapter.MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_income_by_categories, parent, false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
       return listIncomeByCategories?.size ?: 0
    }

    override fun onBindViewHolder(holder: IncomeCategoriesAdapter.MyViewHolder, position: Int) {
        val item = listIncomeByCategories?.get(position)
        holder.txtIncomeNameCategory.text = item?.namaKategori
        holder.txtIncomeNominal.text = ConvertKurs.formatRupiah(item?.totalIncome!!.toInt())
    }


}