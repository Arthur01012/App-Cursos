<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";

// Leer el cuerpo JSON enviado en la solicitud HTTP.
$jsonInput = file_get_contents("php://input");
$datos = json_decode($jsonInput, true);

$camposRequeridos = ["codigo_curso", "matricula", "fecha_registro"];
if (!is_array($datos)) {
    $datos = $_POST;
}

$camposRecibidos = [];
$valores = [];
foreach ($camposRequeridos as $campo) {
    $valor = $datos[$campo] ?? "";
    $valores[$campo] = is_scalar($valor) ? trim((string) $valor) : "";
    $camposRecibidos[$campo] = $valores[$campo] !== "";
}

$codigoCurso = $valores["codigo_curso"];
$matricula = $valores["matricula"];
$fechaRegistro = $valores["fecha_registro"];

//verificar que todos los campos esten llenos
if (in_array(false, $camposRecibidos, true)) {// si alguna esta vacio se envia una respuesta
    $camposFaltantes = array_keys($camposRecibidos, false, true);
    error_log(
        "Inscripción incompleta. Content-Type: " .
        ($_SERVER["CONTENT_TYPE"] ?? "no especificado") .
        "; campos recibidos: " . json_encode($camposRecibidos)
    );
    http_response_code(400);//Faltan los datos requeridos
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe enviar el curso, la matrícula y la fecha de registro.",
        "campos_recibidos" => $camposRecibidos,
        "campos_faltantes" => $camposFaltantes
    ], JSON_UNESCAPED_UNICODE);
    exit;
}//verificacion en fase de prueba-ELIMINAR DESPUES XD
try {
    //verificar que el codigo del curso que se obtuvo se encuentre en la base de datos
    $consultaCurso = $pdo->prepare(
        "SELECT codigo_curso FROM tbl_curso WHERE codigo_curso = ?"
    );//verificacion en fase de prueba-ELIMINAR DESPUES XD
    $consultaCurso->execute([$codigoCurso]);

    // Si el curso no existe, responder con 404 y detener el endpoint
    if (!$consultaCurso->fetch()) {
        error_log("Curso no encontrado en inscripción. codigo_curso recibido: " . $codigoCurso);
        http_response_code(404);//Curso inexistente
        echo json_encode([
            "exito" => false,
            "mensaje" => "El curso indicado no existe.",
            "codigo_curso_recibido" => $codigoCurso
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }//eliminar esta verificacion cuando ya se tenga lista la vista

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
    $pdo->commit();//enviar el commit
    //Devolver la respuesta JSON
    echo json_encode([
        "exito" => true,
        "mensaje" => "Inscripción realizada correctamente.",
        "id_inscripcion" => $idInscripcion
    ], JSON_UNESCAPED_UNICODE);
    //indica una incripcion exitosa

} catch (Exception $e) {
    if ($pdo->inTransaction()) {
        $pdo->rollBack();//se realiza un rollback en caso de error
    }
    //Devolver error junto con el lig
    error_log("Error al registrar inscripción: " . $e->getMessage());
    http_response_code(500);//Error al guardar
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error al registrar la inscripción."
    ], JSON_UNESCAPED_UNICODE);
}

?>