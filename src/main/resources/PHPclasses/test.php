<!--SELECT PDF-->
//<?php
//$host = "mysql:host=localhost;dbname=id22000619_bdtfg";
//$username = "id22000619_sql11652348";
//$password = "rtJrPKLmpc_1";
//
//$obj_conexion = mysqli_connect("localhost",$username,$password,"id22000619_bdtfg");
//if(!$obj_conexion){
//    echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
//}
//
//$sql= "SELECT * FROM FormulariosRegistrados";
//$result = $obj_conexion->query($sql);
//
//if ($result->num_rows > 0) {
//    while($row = $result->fetch_assoc()) {
//        echo $row["NombreForm"]."<br>";
//    }
//
//} else {
//    echo "0 results";
//}
//$obj_conexion->close();
//
//?>

<!--SELECT CARPETA-->
<!---->
<?php
//$host = "mysql:host=localhost;dbname=id22000619_bdtfg";
//$username = "id22000619_sql11652348";
//$password = "rtJrPKLmpc_1";
//
//$obj_conexion = mysqli_connect("localhost",$username,$password,"id22000619_bdtfg");
//if(!$obj_conexion){
//    echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
//}
//
//$sql= "SELECT * FROM CredencialesApp where NombreCampo = 'Carpeta'";
//$result = $obj_conexion->query($sql);
//
//if ($result->num_rows > 0) {
//    while($row = $result->fetch_assoc()) {
//        echo $row["ValorCampo"];
//    }
//
//} else {
//    echo "0 results";
//}
//$obj_conexion->close();
//?>

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


<?php

//      $host = "mysql:host=localhost;dbname=id22000619_bdtfg";
//      $username = "id22000619_sql11652348";
//      $password = "rtJrPKLmpc_1";
//
//      $link = new PDO($host,$username,$password);
//      $link->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$servername = "localhost";
$database = "id22000619_bdtfg";
$username = "id22000619_sql11652348";
$password = "rtJrPKLmpc_1";

$db = new MySQLi($servername,$username,$password,$database);
$v1 = $_GET["cp"];
$v2 = $_GET["nf"];
$v3 = $_GET["pag"];
$v4 = $_GET["posx"];
$v5 = $_GET["posy"];
$v6 = $_GET["l"];
$v7 = $_GET["a"];

//   $sql = " INSERT INTO CamposFormularios (CamposFormularios, NombreFormulario, Pagina, PosicionX, PosicionY, Largo, Ancho) VALUES (?, ?, ?, ?, ?, ?, ?);";
//  $statement = $link->prepare($sql);
echo "hola\n";
echo $v3 ;
echo "hola\n";
//    $statement->execute([$v1,$v2,$v3,$v4,$v5,$v6,$v7]);

$sql = "INSERT INTO `CamposFormularios` (`CamposFormularios`, `NombreFormulario`, `Pagina`, `PosicionX`, `PosicionY`, `Largo`, `Ancho`) VALUES ('$v1', '$v2', '$v3', '$v4', '$v5', '$v6', '$v7');";
// $sql = "INSERT INTO `FormulariosRegistrados` (`NombreForm`) VALUES ('$v2')";
$db->query($sql);


if($db){
    echo "todos los campos registrados con éxito";
}else{
    echo "ha habido un error";
}
