<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";


$jsonInput = file_get_contents("php://input");//Lee el cuerpo sin procesar de la solicitud http
$datos = json_decode($jsonInput, true);//Convierte el JSON a un arreglo asociativo de PHP

//variables
$codigoCurso = isset($datos["codigo_curso"]) ? trim($datos["codigo_curso"]) : "";
$matricula = isset($datos["matricula"]) ? trim($datos["matricula"]) : "";
$correo = isset($datos["correo"]) ? trim($datos["correo"]) : "";
$fechaRegistro = isset($datos["fecha_registro"]) ? trim($datos["fecha_registro"]) : "";

//verificar que todos los campos esten llenos
if (empty($codigoCurso) || empty($matricula) || empty($correo) || empty($fechaRegistro)) {// si alguna esta vacio se envia una respuesta 
    http_response_code(400);
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe enviar el curso, la matrícula, el correo y la fecha de registro."//deben de estar llenos todos los campos
    ], JSON_UNESCAPED_UNICODE);
    exit;
}
try {
    //code...
} catch (\Throwable $th) {
    //throw $th;
}

?>