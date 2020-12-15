package com.teguh.keepfinance.model

import com.google.gson.annotations.SerializedName

data class ResponseIncomeByCategories(

	@field:SerializedName("data")
	val data: ArrayList<DataIncomeByCategories>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class DataIncomeByCategories(

	@field:SerializedName("total_income")
	val totalIncome: String? = null,

	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null
)
