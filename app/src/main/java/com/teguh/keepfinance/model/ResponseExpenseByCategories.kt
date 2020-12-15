package com.teguh.keepfinance.model

import com.google.gson.annotations.SerializedName

data class ResponseExpenseByCategories(

	@field:SerializedName("data")
	val data: ArrayList<DataExpenseByCategories>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)

data class DataExpenseByCategories(

	@field:SerializedName("total_expense")
	val totalExpense: String? = null,

	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null
)
