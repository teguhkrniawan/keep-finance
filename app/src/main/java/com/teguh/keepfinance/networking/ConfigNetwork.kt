package com.teguh.keepfinance.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigNetwork {
    companion object {
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://192.168.43.58/keepfinance/")
                //.baseUrl("http://10.0.0.2/keepfinance/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun transaksiService(): TransaksiService {
            return getRetrofit().create(TransaksiService::class.java)
        }

        fun categorySercice(): CategoryService{
            return getRetrofit().create(CategoryService::class.java)
        }
    }
}