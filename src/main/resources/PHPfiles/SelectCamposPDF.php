    <?php
        $host = "mysql:host=localhost;dbname=id22000619_bdtfg";
        $username = "id22000619_sql11652348";
        $password = "rtJrPKLmpc_1";

        $obj_conexion = mysqli_connect("localhost",$username,$password,"id22000619_bdtfg");
        if(!$obj_conexion){
            echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
        }
        $v1=$_GET["id"];
    
        $sql= "SELECT * FROM CamposFormularios where NombreFormulario='".$v1."'";
        //echo $sql;
        $result = $obj_conexion->query($sql);

        if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
            echo $row["CamposFormularios"]."<b>".$row["NombreFormulario"]."<b>".$row["Pagina"]."<b>".$row["PosicionX"]."<b>".$row["PosicionY"]."<b>".$row["Largo"]."<b>".$row["Ancho"]. "<br>";
          }
             
        } else {
              echo "0 results";
        }
        $obj_conexion->close();
        
    ?>