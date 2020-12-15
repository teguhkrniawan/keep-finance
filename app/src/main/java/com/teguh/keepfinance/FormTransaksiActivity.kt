package com.teguh.keepfinance

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.teguh.keepfinance.helper.DatePickerCLickListener
import com.teguh.keepfinance.helper.DatePickerDialog
import com.teguh.keepfinance.model.DataTransaksi
import com.teguh.keepfinance.model.ResponseAction
import com.teguh.keepfinance.model.ResponseKategori
import com.teguh.keepfinance.model.ResponseTransaksi
import com.teguh.keepfinance.networking.ConfigNetwork
import kotlinx.android.synthetic.main.activity_form_kategori.*
import kotlinx.android.synthetic.main.activity_form_transaksi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FormTransaksiActivity : AppCompatActivity(), DatePickerCLickListener{

    private var jenisFinansial: String? = null
    private var listIdKategori: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_transaksi)

        // parcelable data
        val dataParcel = intent.getParcelableExtra<DataTransaksi>("data_transaksi")

        var idSelected: String? = null
        val txtNominal = findViewById<TextView>(R.id.form_transaksi_nominal)
        val txtTanggal = findViewById<TextView>(R.id.form_transaksi_tanggal)
        val txtKeterangan = findViewById<TextView>(R.id.form_transaksi_keterangan)

        if (dataParcel != null){

            txtKeterangan.text = dataParcel.keteranganTransaksi
            txtTanggal.text = dataParcel.tanggalTransaksi
            txtNominal.text = dataParcel.nominalTransaksi
            form_transaksi_simpan.text = getString(R.string.perbarui)

            if (dataParcel.jenis == "income"){
                (radio_group_transaksi.getChildAt(0) as RadioButton).isChecked = true

                val data = ArrayList<String>()
                data.add(dataParcel.namaKategori!!)

                val adapterSpinner: ArrayAdapter<String> = ArrayAdapter(this@FormTransaksiActivity, android.R.layout.simple_spinner_dropdown_item, data)
                form_transaksi_spinner.adapter = adapterSpinner
            } else if (dataParcel.jenis == "expense"){
                (radio_group_transaksi.getChildAt(1) as RadioButton).isChecked = true

                val data = ArrayList<String>()
                data.add(dataParcel.namaKategori!!)

                val adapterSpinner: ArrayAdapter<String> = ArrayAdapter(this@FormTransaksiActivity, android.R.layout.simple_spinner_dropdown_item, data)
                form_transaksi_spinner.adapter = adapterSpinner
            }
        }

        radio_group_transaksi.setOnCheckedChangeListener { radioGroup, id ->
            val radioItem = findViewById<RadioButton>(id)
            jenisFinansial = radioItem.text.toString().toLowerCase()

            if (jenisFinansial == "income"){
                idSelected = null
                listIdKategori.clear()
                generateSpinnerData(jenisFinansial)
            } else if (jenisFinansial == "expense"){
                idSelected = null
                listIdKategori.clear()
                generateSpinnerData(jenisFinansial)
            }
        }

        form_transaksi_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
               if (listIdKategori.size < 1){
                   idSelected = dataParcel?.idKategori
               } else {
                   idSelected = listIdKategori[position].toString()
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        if (form_transaksi_simpan.text == getString(R.string.simpan)){
            form_transaksi_simpan.setOnClickListener {
                val nominal = txtNominal.text.toString()
                val tanggal = txtTanggal.text.toString()
                val keterangan = txtKeterangan.text.toString()
                addToTransaksi(idSelected, jenisFinansial, nominal, keterangan, tanggal)
            }
        } else {
            form_transaksi_simpan.setOnClickListener {
                val nominal = txtNominal.text.toString()
                val tanggal = txtTanggal.text.toString()
                val keterangan = txtKeterangan.text.toString()
                val idTransaksi = dataParcel?.idTransaksi
                updateToTransaksi(idSelected, jenisFinansial, nominal, keterangan, tanggal, idTransaksi)
            }
        }

        form_transaksi_pilih_tanggal.setOnClickListener {
            val datePicker = DatePickerDialog()
            datePicker.show(supportFragmentManager, "date_picker_dialog")
        }

    }

    private fun updateToTransaksi(
        idSelected: String?,
        jenisFinansial: String?,
        nominal: String,
        keterangan: String,
        tanggal: String,
        idTransaksi: String?
    ) {
        val update = ConfigNetwork.transaksiService().updateTransaksi(
            idTransaksi,
            idSelected,
            keterangan,
            nominal,
            tanggal
        )
        update.enqueue(object : Callback<ResponseAction>{
            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@FormTransaksiActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
               if (response.isSuccessful){
                   val message = response.body()?.message
                   Toast.makeText(this@FormTransaksiActivity, message, Toast.LENGTH_SHORT).show()
                   finish()
               }
            }

        })
    }

    private fun addToTransaksi(
        idSelected: String?,
        jenisFinansial: String?,
        nominal: String,
        keterangan: String,
        tanggal: String
    ) {
        val insert = ConfigNetwork.transaksiService().addTransaksi(idSelected, keterangan, nominal, tanggal)
        insert.enqueue(object : Callback<ResponseAction>{
            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(this@FormTransaksiActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
               if (response.isSuccessful){
                   val message = response.body()?.message
                   Toast.makeText(this@FormTransaksiActivity, "$message", Toast.LENGTH_SHORT).show()
                   finish()
               }
            }

        })
    }

    private fun generateSpinnerData(jenis: String?) {
        val dataResponse = ConfigNetwork.categorySercice().getJenisKategori(jenis)
        dataResponse.enqueue(object : Callback<ResponseKategori>{
            override fun onFailure(call: Call<ResponseKategori>, t: Throwable) {
                Toast.makeText(this@FormTransaksiActivity, "Error : ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseKategori>,
                response: Response<ResponseKategori>
            ) {
               val data = response.body()?.data
               val namaKategoriList: ArrayList<String> = ArrayList()
                if (data != null) {
                    for (item in data){
                        namaKategoriList.add(item.namaKategori!!)
                        listIdKategori.add(item.idKategori!!)
                    }
                }
                val adapterSpinner: ArrayAdapter<String> = ArrayAdapter(this@FormTransaksiActivity, android.R.layout.simple_spinner_dropdown_item, namaKategoriList)
                form_transaksi_spinner.adapter = adapterSpinner
            }

        })
    }

    override fun tanggalSelected(year: Int, month: Int, hari: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,hari)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        form_transaksi_tanggal.setText(dateFormat.format(calendar.time))
    }
}