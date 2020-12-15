<?php

include_once('koneksi.php');

/**
 * Ada dua parameter yang akan direquest dari
 * frontend yaitu kategori jenis income dan expense
 */

 if(!empty($_GET['jenis'])){
     $jenis = $_GET['jenis'];
     $query = "SELECT * FROM tb_kategori WHERE jenis = '$jenis'";
 } else {
     $query = "SELECT * FROM tb_kategori";
 }

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
