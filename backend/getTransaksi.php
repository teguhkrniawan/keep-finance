<?php

include_once('koneksi.php');

/**
 * Ada dua parameter yang akan direquest dari
 * frontend yaitu kategori jenis income dan expense
 */

 if(!empty($_GET['jenis'])){
     $jenis = $_GET['jenis'];
     $query = "SELECT * FROM tb_transaksi JOIN tb_kategori ON tb_transaksi.id_kategori = tb_kategori.id_kategori WHERE jenis = '$jenis' ";
 } else {
     $query = "SELECT * FROM tb_transaksi JOIN tb_kategori ON tb_transaksi.id_kategori = tb_kategori.id_kategori ";
 }

 $query_get_total_income = "SELECT SUM(nominal_transaksi) as total_income
                            FROM tb_transaksi JOIN tb_kategori 
                            ON tb_transaksi.id_kategori = tb_kategori.id_kategori 
                            WHERE jenis = 'income'";

$query_get_total_expense = "SELECT SUM(nominal_transaksi) as total_expense
                            FROM tb_transaksi JOIN tb_kategori 
                            ON tb_transaksi.id_kategori = tb_kategori.id_kategori 
                            WHERE jenis = 'expense'";

// get semua data transaksi
 $get = mysqli_query($connect, $query);

// get data total income
$get_total_income = mysqli_query($connect, $query_get_total_income);

// get data total expense
$get_total_expense = mysqli_query($connect, $query_get_total_expense);

 $total_income = 0;
 $total_expense = 0;
 $data = array();

 if(mysqli_num_rows($get) > 0){
    while($row = mysqli_fetch_assoc($get)) {
        $data[] = $row;
    }
    while($row2 = mysqli_fetch_assoc($get_total_income)){
        $total_income = $row2['total_income'];
    }
    while($row3 = mysqli_fetch_assoc($get_total_expense)){
        $total_expense = $row3['total_expense'];
    }
    set_response(true, "Data ditemukan", $data, $total_income, $total_expense);
} else {
    set_response(true, "Tidak ada data", $data, $total_income, $total_expense);
}

function set_response($isSuccess, $message, $data, $total_income, $total_expense){
    $result = array(
        'isSuccess' => $isSuccess,
        'message'   => $message,
        'data'      => $data,
        'total_income'  => $total_income,
        'total_expense' => $total_expense
    );

    echo json_encode($result);
}
