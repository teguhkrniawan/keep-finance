package com.teguh.keepfinance.helper

import com.teguh.keepfinance.model.DataTransaksi

object Commons {

    // insialisai view type untuk recyclerview di MainActivity
    val VIEW_TYPE_GROUP = 0
    val VIEW_TYPE_TRANSAKSI = 1

    // function dummy generate data Transaksi
    fun generateDummyTransaksi() : ArrayList<DataTransaksi>{

        val transaksiList = ArrayList<DataTransaksi>()

        var transaksi = DataTransaksi(
            "200000",
            "Membeli buah apel, jeruk, dan anggur",
            "1",
            "2020-12-09",
            "expense",
            "1",
            "Pembelian Topping Minuman"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "120000",
            "Membeli kopi grade A 1 Kg",
            "2",
            "2020-12-10",
            "expense",
            "2",
            "Pembelian Bahan Minuman"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "85000",
            "12 Kopi Cup terjual",
            "3",
            "2020-12-10",
            "income",
            "3",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "95000",
            "7 Kopi Cup terjual",
            "3",
            "2020-12-11",
            "income",
            "4",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "6000",
            "2 Vanila Cup terjual",
            "3",
            "2020-12-10",
            "income",
            "5",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "35000",
            "11 Hezelnut Cup terjual",
            "3",
            "2020-12-16",
            "income",
            "6",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "90000",
            "9 Coco Cup terjual",
            "3",
            "2020-12-16",
            "income",
            "7",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        transaksi = DataTransaksi(
            "20000",
            "12 Teh Cup terjual",
            "3",
            "2020-12-14",
            "income",
            "8",
            "Penjualan"
        )
        transaksiList.add(transaksi)

        return transaksiList
    }

    // function sorting tanggal pada list
    fun sortingList(list: ArrayList<DataTransaksi>?): ArrayList<DataTransaksi>? {
        list?.sortWith(Comparator { dataTransakasi1, dataTransaksi2 ->
            dataTransakasi1.tanggalTransaksi!!.compareTo(dataTransaksi2.tanggalTransaksi!!)
        })
        return list
    }

    // function tambahin ke grup tanggal
    fun addGroupByDate(list: ArrayList<DataTransaksi>?) : ArrayList<DataTransaksi>?{
        var i = 0
        var customList = ArrayList<DataTransaksi>()
        var firstTransaksi = DataTransaksi()

        if (list?.size != 0){
            firstTransaksi.tanggalTransaksi = list?.get(0)?.tanggalTransaksi.toString()
            firstTransaksi.viewType = VIEW_TYPE_GROUP
            customList.add(firstTransaksi)

            while (i < list?.size!!.minus(1)){
                val transaksi = DataTransaksi()
                val tanggal1 = list[i].tanggalTransaksi
                val tanggal2 = list[i+1].tanggalTransaksi

                if (tanggal1 == tanggal2){
                    list[i].viewType = VIEW_TYPE_TRANSAKSI
                    customList.add(list[i])
                } else {
                    list[i].viewType = VIEW_TYPE_TRANSAKSI
                    customList.add(list[i])
                    transaksi.tanggalTransaksi = tanggal2
                    transaksi.viewType = VIEW_TYPE_GROUP
                    customList.add(transaksi)
                }
                i++
            }
            customList.add(list[i])
            return customList
        }
        return customList
    }
}