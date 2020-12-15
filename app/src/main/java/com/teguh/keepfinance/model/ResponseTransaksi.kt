package com.teguh.keepfinance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTransaksi(

	@field:SerializedName("data")
	val data: ArrayList<DataTransaksi>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null,

	@field:SerializedName("total_income")
	val totalIncome: String? = null,

	@field:SerializedName("total_expense")
	val totalExpense: String? = null

) : Parcelable

@Parcelize
data class DataTransaksi(

	@field:SerializedName("nominal_transaksi")
	val nominalTransaksi: String? = null,

	@field:SerializedName("keterangan_transaksi")
	val keteranganTransaksi: String? = null,

	@field:SerializedName("id_kategori")
	val idKategori: String? = null,

	@field:SerializedName("tanggal_transaksi")
	var tanggalTransaksi: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("id_transaksi")
	val idTransaksi: String? = null,

	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null,

	var viewType: Int? = 1
) : Parcelable
