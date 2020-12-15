package com.teguh.keepfinance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseKategori(

	@field:SerializedName("data")
	val data: ArrayList<DataKategori>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

@Parcelize
data class DataKategori(

	@field:SerializedName("id_kategori")
	val idKategori: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null
): Parcelable
