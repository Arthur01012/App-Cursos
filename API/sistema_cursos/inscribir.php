<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";


$jsonInput = file_get_contents("php://input");//Lee el cuerpo sin procesar de la solicitud http
$datos = json_decode($jsonInput, true);//Convierte el JSON a un arreglo asociativo de PHP


?>