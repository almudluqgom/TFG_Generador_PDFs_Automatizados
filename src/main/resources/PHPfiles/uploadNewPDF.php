<?php

    
    $servername = "localhost";
    $database = "id22000619_bdtfg";
    $username = "id22000619_sql11652348";
    $password = "rtJrPKLmpc_1";
    
    $db = new MySQLi($servername,$username,$password,$database);
    $v2 = $_GET["valor"];
    $sql = "INSERT INTO `FormulariosRegistrados` (`NombreForm`) VALUES ('$v2')";
    $db->query($sql);
    
    if($db){
        echo "PDF registrado con éxito";
    }else{
        echo "ha habido un error";
    }
?>