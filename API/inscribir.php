<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";

// Leer el cuerpo JSON enviado en la solicitud HTTP.
$jsonInput = file_get_contents("php://input");
$datos = json_decode($jsonInput, true);

if (!is_array($datos)) {
    $datos = $_POST;
}

$codigoCurso = trim((string) ($datos["codigo_curso"] ?? ""));
$matricula = trim((string) ($datos["matricula"] ?? ""));
$fechaRegistro = trim((string) ($datos["fecha_registro"] ?? ""));

try {
    $consultaAlumno = $pdo->prepare(
        "SELECT matricula FROM tbl_alumnos WHERE matricula = ?"
    );
    $consultaAlumno->execute([$matricula]);
    if (!$consultaAlumno->fetch()) {
        http_response_code(404);
        echo json_encode([
            "exito" => false,
            "mensaje" => "No existe un alumno registrado con esa matrícula."
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }

    $pdo->beginTransaction();

    // Crear la inscripción asociando el curso, el alumno y la fecha recibidos
    $inscripcion = $pdo->prepare(
        "INSERT INTO tbl_inscripciones (codigo_curso, matricula, fecha_registro) VALUES (?, ?, ?)"
    );
    $inscripcion->execute([$codigoCurso, $matricula, $fechaRegistro]);

    // Obtener el ID generado y confirmar los cambios de la transacción
    $idInscripcion = (int) $pdo->lastInsertId();
    $pdo->commit();
    echo json_encode([
        "exito" => true,
        "mensaje" => "Inscripción realizada correctamente.",
        "id_inscripcion" => $idInscripcion
    ], JSON_UNESCAPED_UNICODE);

} catch (Exception $e) {
    if ($pdo->inTransaction()) {
        $pdo->rollBack();
    }
    error_log("Error al registrar inscripción: " . $e->getMessage());
    http_response_code(500);
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error al registrar la inscripción."
    ], JSON_UNESCAPED_UNICODE);
}

?>