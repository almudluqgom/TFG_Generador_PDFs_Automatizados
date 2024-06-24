
<?php
        $host = "mysql:host=localhost;dbname=id22000619_bdtfg";
        $username = "id22000619_sql11652348";
        $password = "rtJrPKLmpc_1";

        $con = mysqli_connect("localhost",$username,$password,"id22000619_bdtfg");
        
       if (mysqli_connect_errno()){
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }
  
        $v1= $_GET['CamposFormularios'];
        $v2 = $_GET['NombreFormulario'];
        $v3 = $_GET['Pagina'];
        $v4 = $_GET['PosicionX'];
        $v5 = $_GET['PosicionY'];
        $v6 = $_GET['Largo'];
        $v7 = $_GET['Ancho'];
        
        $sql = "INSERT INTO CamposFormularios(CamposFormularios, NombreFormulario, Pagina, PosicionX, PosicionY, Largo,Ancho) VALUES ('$v1','$v2','$v3','$v4','$v5','$v6','$v7')";

         if(mysqli_query($con, $sql))
           echo "Values have been inserted";
        
        else
            echo "no salió bien";
            mysqli_close($con);



?>