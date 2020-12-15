<?php

include_once ('koneksi.php');

if(!empty($_POST['id_transaksi'])) {
    $id_transaksi = $_POST['id_transaksi'];
    $query = "DELETE FROM tb_transaksi WHERE id_transaksi='$id_transaksi'";
    $delete = mysqli_query($connect, $query);
    if($delete){
        set_response(true, "Berhasil menghapus data transaksi");
    } else {
        set_response(false, "Gagal menghapus item transaksi");
    }  
} else {
    set_response(false, "parameter id_transaksi wajib di isi");
}

function set_response($isSuccess, $message) {
    $result = array(
        'is_success' =>  $isSuccess,
        'message'   =>  $message
    );

    echo json_encode($result);
}