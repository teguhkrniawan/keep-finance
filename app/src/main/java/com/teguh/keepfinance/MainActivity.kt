package com.teguh.keepfinance

import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.teguh.keepfinance.adapter.TransaksiAdapter
import com.teguh.keepfinance.adapter.TransaksiItemClickListener
import com.teguh.keepfinance.helper.Commons
import com.teguh.keepfinance.helper.ConvertKurs
import com.teguh.keepfinance.model.DataTransaksi
import com.teguh.keepfinance.model.ResponseAction
import com.teguh.keepfinance.model.ResponseTransaksi
import com.teguh.keepfinance.networking.ConfigNetwork
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal var transaksiList = ArrayList<DataTransaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dummy data transaksi
        //generateTransaksi()

//        val transaksiAdapter = TransaksiAdapter(this, transaksiList)
//        rv_main_transaksi.adapter = transaksiAdapter

        showTransaksi()

        // event income by categories ketika di klik
        cv_main_income.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }

        // event expense by categories ketika di klik
        cv_main_expense.setOnClickListener {
            startActivity(Intent(this, ExpenseActivity::class.java))
        }

        // event manage categories
        cv_main_category.setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }

        main_fab_add.setOnClickListener {
            startActivity(Intent(this, FormTransaksiActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        showTransaksi()
    }

    private fun showTransaksi() {
        val dataTransaksi = ConfigNetwork.transaksiService().getAllTransaksi()
        dataTransaksi.enqueue(object : Callback<ResponseTransaksi>{
            override fun onFailure(call: Call<ResponseTransaksi>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseTransaksi>,
                response: Response<ResponseTransaksi>
            ) {
                if (response.isSuccessful){

                    val totalIncome = response.body()?.totalIncome ?: "0"
                    val totalExpense = response.body()?.totalExpense ?: "0"

                    val totalKeuntungan : Int? = totalIncome.toInt() - totalExpense.toInt()
                    main_total_txt.text = ConvertKurs.formatRupiah(totalKeuntungan ?: 0)

                    var data: ArrayList<DataTransaksi>? = ArrayList()
                    data?.clear()

                    data = response.body()?.data
                    data = Commons.sortingList(data)
                    data = Commons.addGroupByDate(data)

                    TransaksiAdapter().resetDataTransaksi()
                    val transaksiAdapter = TransaksiAdapter(this@MainActivity, data, object : TransaksiItemClickListener{
                        override fun detail(item: DataTransaksi?) {
                           val intent = Intent(this@MainActivity, FormTransaksiActivity::class.java)
                           intent.putExtra("data_transaksi", item)
                           startActivity(intent)
                        }

                        override fun hapus(item: DataTransaksi?) {
                            AlertDialog.Builder(this@MainActivity).apply {
                                setTitle("Hapus data")
                                setMessage("Yakin ingin hapus ini ?")
                                setPositiveButton("Hapus") { dialogInterface, i ->
                                    deleteFromTransaksi(item?.idTransaksi)
                                    dialogInterface.dismiss()
                                }
                                setNegativeButton("Batal") { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                }
                            }.show()
                        }
                    })

                    rv_main_transaksi.adapter = transaksiAdapter
                    if (rv_main_transaksi.itemDecorationCount == 0)
                        rv_main_transaksi.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                }
            }

        })
    }

    private fun deleteFromTransaksi(item: String?) {
        val delete = ConfigNetwork.transaksiService().deleteTransaksi(item)
        delete.enqueue(object : Callback<ResponseAction>{
            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                if (response.isSuccessful){
                    val message = response.body()?.message
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    showTransaksi()
                } else {
                    Toast.makeText(this@MainActivity, "Response Failed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    // functiom dummy transaksi
    private fun generateTransaksi() {
        transaksiList = Commons.generateDummyTransaksi()
        //transaksiList = Commons.sortingList(transaksiList)
        //transaksiList = Commons.addGroupByDate(transaksiList)
        for (item in transaksiList){
            Log.i("INFO", "Tanggal : " +item.tanggalTransaksi)
            Log.i("INFO", "Nominal : " +item.nominalTransaksi)
            Log.i("INFO", "viewType : " +item.viewType)
            Log.i("INFO", "================================" )
        }
    }
}