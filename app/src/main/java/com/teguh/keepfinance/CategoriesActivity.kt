package com.teguh.keepfinance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.teguh.keepfinance.adapter.KategoriAdapter
import com.teguh.keepfinance.adapter.KategoriItemClickListener
import com.teguh.keepfinance.model.DataKategori
import com.teguh.keepfinance.model.ResponseAction
import com.teguh.keepfinance.model.ResponseKategori
import com.teguh.keepfinance.networking.ConfigNetwork
import kotlinx.android.synthetic.main.activity_categories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        generateAllKategori()

        fab_add.setOnClickListener{
            startActivity(Intent(this, FormKategoriActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        generateAllKategori()
    }

    private fun generateAllKategori() {
        val dataKategori = ConfigNetwork.categorySercice().getKategori()
        dataKategori.enqueue(object : Callback<ResponseKategori>{
            override fun onFailure(call: Call<ResponseKategori>, t: Throwable) {
                Toast.makeText(this@CategoriesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseKategori>,
                response: Response<ResponseKategori>
            ) {
                if (response.isSuccessful){
                    val listKategori = response.body()?.data
                    val kategoriAdapter = KategoriAdapter(listKategori, object: KategoriItemClickListener{
                        // ketika item di klik akan menuju detail form kategori
                        override fun detailItem(item: DataKategori?) {
                            val intent = Intent(this@CategoriesActivity, FormKategoriActivity::class.java)
                            intent.putExtra("data_kategori", item)
                            startActivity(intent)
                        }
                        // ketika imageview hapus button diklik
                        override fun hapusItem(item: DataKategori?) {
                            AlertDialog.Builder(this@CategoriesActivity).apply {
                                setTitle("Hapus data")
                                setMessage("Yakin ingin hapus ${item?.namaKategori}")
                                setPositiveButton("Hapus") { dialogInterface, i ->
                                    deleteKategori(item?.idKategori)
                                    dialogInterface.dismiss()
                                }
                                setNegativeButton("Batal") { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                }
                            }.show()
                        }

                    })
                    categories_rv.adapter = kategoriAdapter
                    categories_rv.addItemDecoration(DividerItemDecoration(this@CategoriesActivity, LinearLayoutManager.VERTICAL))
                }
            }

        })
    }

    private fun deleteKategori(idKategori: String?) {
        val hapus = ConfigNetwork.categorySercice().hapusKategori(idKategori)
        hapus.enqueue(object : Callback<ResponseAction>{
            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@CategoriesActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if (response.isSuccessful){
                    val message = response.body()?.message
                    Toast.makeText(this@CategoriesActivity, "$message", Toast.LENGTH_SHORT).show()
                    generateAllKategori()
                }
            }

        })
    }
}