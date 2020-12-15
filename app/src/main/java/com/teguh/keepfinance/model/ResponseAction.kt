package com.teguh.keepfinance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAction(

	@field:SerializedName("is_success")
	val isSuccess: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
