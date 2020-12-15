<?php

include_once('koneksi.php');

$query = "SELECT SUM(nominal_transaksi) as total_expense, tb_kategori.nama_kategori 
          FROM tb_transaksi JOIN tb_kategori ON tb_transaksi.id_kategori = tb_kategori.id_kategori 
          WHERE jenis = 'expense'
          GROUP BY tb_kategori.nama_kategori ";

$get = mysqli_query($connect, $query);

 $data = array();

 if(mysqli_num_rows($get) > 0){
    while($row = mysqli_fetch_assoc($get)) {
        $data[] = $row;
    }
    set_response(true, "Data ditemukan", $data);
} else {
    set_response(true, "Tidak ada data", $data);
}

function set_response($isSuccess, $message, $data){
    $result = array(
        'isSuccess' => $isSuccess,
        'message'   => $message,
        'data'      => $data
    );

    echo json_encode($result);
}
