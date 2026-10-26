<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";

// Leer el cuerpo JSON enviado en la solicitud HTTP.
$jsonInput = file_get_contents("php://input");
$datos = json_decode($jsonInput, true);

// Obtener los datos que se utilizarán en las consultas a la base de datos
//primero se comprueba que haya un valor en el campo, si es asi elimina los espacion en blanco
$codigoCurso = isset($datos["codigo_curso"]) ? trim($datos["codigo_curso"]) : "";
$matricula = isset($datos["matricula"]) ? trim($datos["matricula"]) : "";
$correo = isset($datos["correo"]) ? trim($datos["correo"]) : "";
$fechaRegistro = isset($datos["fecha_registro"]) ? trim($datos["fecha_registro"]) : "";

//verificar que todos los campos esten llenos
if (empty($codigoCurso) || empty($matricula) || empty($correo) || empty($fechaRegistro)) {// si alguna esta vacio se envia una respuesta 
    http_response_code(400);//Faltan los datos requeridos
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe enviar el curso, la matrícula, el correo y la fecha de registro."//deben de estar llenos todos los campos
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
        http_response_code(404);//Curso inexistente
        echo json_encode([
            "exito" => false,
            "mensaje" => "El curso indicado no existe."
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }//eliminar esta verificacion cuando ya se tenga lista la vista

    // Agrupar el alta del alumno y la inscripción para guardar ambas operaciones juntas
    $pdo->beginTransaction();

    // Buscar si la matrícula ya está registrada como alumno
    $consultaAlumno = $pdo->prepare(
        "SELECT matricula FROM tbl_alumnos WHERE matricula = ?"
    );
    $consultaAlumno->execute([$matricula]);
    $alumno = $consultaAlumno->fetch();

    // Si el alumno todavía no existe, registrarlo con su correo    
    if (!$alumno) {
        $nuevoAlumno = $pdo->prepare(
            "INSERT INTO tbl_alumnos (matricula, correo) VALUES (?, ?)"
        );
        $nuevoAlumno->execute([$matricula, $correo]);
    }

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