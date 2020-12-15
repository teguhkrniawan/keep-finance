<?php

include_once('koneksi.php');

if(!empty($_POST['id_kategori'] && 
          $_POST['keterangan_transaksi'] && 
          $_POST['nominal_transaksi'] && 
          $_POST['tanggal_transaksi']))
{
  
    $id_kategori = $_POST['id_kategori'];
    $keterangan_transaksi = $_POST['keterangan_transaksi'];
    $nominal_transaksi = $_POST['nominal_transaksi'];
    $tanggal_transaksi = $_POST['tanggal_transaksi'];

    // Query SQL
    $query = "INSERT INTO tb_transaksi(id_kategori, keterangan_transaksi, nominal_transaksi, tanggal_transaksi) 
              VALUES ('$id_kategori', '$keterangan_transaksi', '$nominal_transaksi', '$tanggal_transaksi')";

    if(mysqli_query($connect, $query)){
        set_response(true, "Berhasil menyimpan data transaksi");
    } else {
        set_response(false, "Gagal menyimpan data transaksi");
    }
} else {
    set_response(false, "Harap cek kembali request data yang di minta");
}

// function mengubah ke json 
function set_response($is_success, $message){
    $result = array(
        'is_success'    =>  $is_success,
        'message'       =>  $message
    );
    echo json_encode($result);
}
