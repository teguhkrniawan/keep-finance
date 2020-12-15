<?php

include_once('koneksi.php');

if(!empty($_POST['nama_kategori'] && !empty($_POST['jenis']))){
  
    $nama_kategori = $_POST['nama_kategori'];
    $jenis = $_POST['jenis'];

    // Query SQL
    $query = "INSERT INTO tb_kategori(nama_kategori, jenis) VALUES ('$nama_kategori', '$jenis')";

    if(mysqli_query($connect, $query)){
        set_response(true, "Berhasil menyimpan data kategori");
    } else {
        set_response(false, "Gagal menyimpan data kategori");
    }
} else {
    set_response(false, "Nama kategori dan jenis wajib diberikan request");
}

// function mengubah ke json 
function set_response($is_success, $message){
    $result = array(
        'is_success'    =>  $is_success,
        'message'       =>  $message
    );
    echo json_encode($result);
}
