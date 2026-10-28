<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";

$matricula = isset($_GET["matricula"]) ? trim($_GET["matricula"]) : "";

if ($matricula === "") {
    http_response_code(400);
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe enviar la matrícula del alumno."
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

try {
    $consulta = $pdo->prepare(
        "SELECT matricula, correo FROM tbl_alumnos WHERE matricula = ?"
    );
    $consulta->execute([$matricula]);
    $alumno = $consulta->fetch();

    if (!$alumno) {
        http_response_code(404);
        echo json_encode([
            "exito" => false,
            "mensaje" => "No existe un alumno registrado con esa matrícula."
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }

    echo json_encode($alumno, JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    error_log("Error al consultar matrícula de alumno: " . $e->getMessage());
    http_response_code(500);
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error al consultar los datos del alumno."
    ], JSON_UNESCAPED_UNICODE);
}
?>
