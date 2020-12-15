<?php

include_once ('koneksi.php');

if(!empty($_POST['id_kategori'])) {
    $id_kategori = $_POST['id_kategori'];
    $query = "DELETE FROM tb_kategori WHERE id_kategori='$id_kategori'";
    $delete = mysqli_query($connect, $query);
    if($delete){
        set_response(true, "Berhasil menghapus data kategori");
    } else {
        set_response(false, "Gagal menghapus item kategori karena kategori sudah digunakan");
    }  
} else {
    set_response(false, "parameter id_kategori wajib di isi");
}

function set_response($isSuccess, $message) {
    $result = array(
        'isSuccess' =>  $isSuccess,
        'message'   =>  $message
    );

    echo json_encode($result);
}