<?php

include_once('koneksi.php');

if(!empty($_POST['id_transaksi'] &&
          $_POST['id_kategori'] && 
          $_POST['keterangan_transaksi'] && 
          $_POST['nominal_transaksi'] && 
          $_POST['tanggal_transaksi']))
{
 
    $id_transaksi   = $_POST['id_transaksi'];
    $id_kategori    = $_POST['id_kategori'];
    $keterangan_transaksi   = $_POST['keterangan_transaksi'];
    $nominal_transaksi      = $_POST['nominal_transaksi'];
    $tanggal_transaksi      = $_POST['tanggal_transaksi'];

    // Query SQL
    $query = "UPDATE tb_transaksi SET id_kategori='$id_kategori', 
                     keterangan_transaksi='$keterangan_transaksi',
                     nominal_transaksi='$nominal_transaksi',
                     tanggal_transaksi='$tanggal_transaksi' 
              WHERE id_transaksi='$id_transaksi' ";

    if(mysqli_query($connect, $query)){
        set_response(true, "Berhasil perbaharui data transaksi");
    } else {
        set_response(false, "Gagal perbaharui data transaksi");
    }
} else {
    set_response(false, "Beberapa request belum diset atau salah");
}

// function mengubah ke json 
function set_response($is_success, $message){
    $result = array(
        'is_success'    =>  $is_success,
        'message'       =>  $message
    );
    echo json_encode($result);
}
