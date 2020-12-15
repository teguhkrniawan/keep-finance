<?php

include_once('koneksi.php');

if(!empty($_POST['nama_kategori'] && !empty($_POST['jenis'])) && !empty($_POST['id_kategori'])){
  
    $id_kategori = $_POST['id_kategori'];
    $nama_kategori = $_POST['nama_kategori'];
    $jenis = $_POST['jenis'];

    // Query SQL
    $query = "UPDATE tb_kategori SET nama_kategori='$nama_kategori', jenis='$jenis' WHERE id_kategori='$id_kategori' ";

    if(mysqli_query($connect, $query)){
        set_response(true, "Berhasil perbaharui data kategori");
    } else {
        set_response(false, "Gagal perbaharui data kategori");
    }
} else {
    set_response(false, "Nama kategori, id kategori, dan jenis wajib diberikan request");
}

// function mengubah ke json 
function set_response($is_success, $message){
    $result = array(
        'is_success'    =>  $is_success,
        'message'       =>  $message
    );
    echo json_encode($result);
}
