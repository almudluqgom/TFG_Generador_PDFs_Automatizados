    <?php
        $host = "mysql:host=localhost;dbname=id22000619_bdtfg";
        $username = "id22000619_sql11652348";
        $password = "rtJrPKLmpc_1";

        $obj_conexion = mysqli_connect("localhost",$username,$password,"id22000619_bdtfg");
        
        if(!$obj_conexion){
            echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
        }
        $sql= "SELECT * FROM CredencialesApp where NombreCampo = 'Carpeta'";
        
        $result = $obj_conexion->query($sql);
        
        if (!$result){
           echo 'Query error: ' . $mysqli->error();
           die();
        }
        $row = $result->fetch_assoc();
        
        if (!$row){
           echo 'Warning: no rows found.';
           die();
        }
        
        echo   $row['ValorCampo']  ;
    ?>