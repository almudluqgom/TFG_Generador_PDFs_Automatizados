<?php

    $servername = "localhost";
    $database = "id22000619_bdtfg";
    $username = "id22000619_sql11652348";
    $password = "rtJrPKLmpc_1";
    
    $db = new MySQLi($servername,$username,$password,$database);
    $v2 = $_GET["valor"];
    $sql = "UPDATE `CredencialesApp` SET `ValorCampo`='$v2' WHERE `NombreCampo`='Carpeta';";
    //$sql = "UPDATE `CredencialesApp` SET ValorCampo='$v2' WHERE NombreCampo='Carpeta'";
    $db->query($sql);
    
    if($db){
        echo "Actualizada carpeta con éxito";
    }else{
        echo "ha habido un error";
    }
?>