<?php

header("Content-Type: application/json");

// Se trae el archivo de la conexion a la base de datos
require_once "config/conexion.php";

try {
    // Se hace la query para traer todas las carreras de la base de datos
    $stmt = $pdo->query("SELECT idCarrera, nombreCarrera FROM tbl_carrera ORDER BY nombreCarrera ASC");
    $carreras = $stmt->fetchAll();

    echo json_encode($carreras);

} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al obtener carreras: " . $e->getMessage()]);
}
?>