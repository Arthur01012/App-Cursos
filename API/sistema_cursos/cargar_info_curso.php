<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "./config/conexion.php";

// Obtener el código del curso enviado como parámetro GET
$codigoCurso = isset($_GET["codigo_curso"]) ? trim($_GET["codigo_curso"]) : "";

// Comprobar que se haya enviado el código del curso
if ($codigoCurso === "") {
    http_response_code(400);//Faltan los datos requeridos
    echo json_encode([
        "exito" => false,
        "mensaje" => "Debe enviar el código del curso."
    ], JSON_UNESCAPED_UNICODE);
    exit;
}//verificacion en fase de prueba-ELIMINAR DESPUES XD

try {
    // Preparar una consulta para obtener el código, el nombre y la ruta de imagen del curso
    $stmt = $pdo->prepare(  
        "SELECT codigo_curso, titulo_curso, ruta_img_grid
         FROM tbl_curso
         WHERE codigo_curso = ?"
    );
    // Ejecutar la consulta con el código recibido
    $stmt->execute([$codigoCurso]);
    // Obtener los datos del curso encontrado
    $curso = $stmt->fetch();

    // Si no existe un curso con ese código, responder con un error 404
    if (!$curso) {
        http_response_code(404);//Curso inexistente
        echo json_encode([
            "exito" => false,
            "mensaje" => "No se encontró el curso solicitado."
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }//verificacion en fase de prueba-ELIMINAR DESPUES XD

    // Devolver la información del curso como JSON, conservando tildes y caracteres especiales
    echo json_encode($curso, JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    // Registrar el detalle técnico en el log del servidor
    error_log("Error al cargar información del curso: " . $e->getMessage());
    http_response_code(500);//Error al obtener la información
    echo json_encode([
        "exito" => false,
        "mensaje" => "Error al obtener la información del curso."
    ], JSON_UNESCAPED_UNICODE);
}
?>