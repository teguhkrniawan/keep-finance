package com.teguh.keepfinance.networking

import com.teguh.keepfinance.model.ResponseAction
import com.teguh.keepfinance.model.ResponseKategori
import retrofit2.Call
import retrofit2.http.*

interface CategoryService {

    @GET("getKategori.php")
    fun getKategori(): Call<ResponseKategori>

    @GET("getKategori.php")
    fun getJenisKategori(
        @Query("jenis") jenis: String?
    ): Call<ResponseKategori>

    @FormUrlEncoded
    @POST("tambahKategori.php")
    fun tambahKategori(
        @Field("nama_kategori") namaKategori: String,
        @Field("jenis") jenis: String
    ): Call<ResponseAction>

    @FormUrlEncoded
    @POST("hapusKategori.php")
    fun hapusKategori(
        @Field("id_kategori") idKategori: String?
    ): Call<ResponseAction>

    @FormUrlEncoded
    @POST("updateKategori.php")
    fun updatePengunjung(
        @Field("id_kategori") idKategori: String?,
        @Field("nama_kategori") namaKategori: String,
        @Field("jenis") jenis: String
    ): Call<ResponseAction>
}