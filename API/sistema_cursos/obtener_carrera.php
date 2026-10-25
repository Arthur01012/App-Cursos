<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "config/conexion.php";

$idCarrera = isset($_GET['idCarrera']) ? intval($_GET['idCarrera']) : 0;

try {
    if ($idCarrera > 0) {
        $stmt = $pdo->prepare("SELECT codigo_curso, idCarrera, titulo_curso, descripcion, espacio, total_horas, dias_atencion, horas_atencion, ruta_img_grid, capacidad FROM tbl_curso WHERE idCarrera = ?");
        $stmt->execute([$idCarrera]);
    } else {
        $stmt = $pdo->query("SELECT codigo_curso, idCarrera, titulo_curso, descripcion, espacio, total_horas, dias_atencion, horas_atencion, ruta_img_grid, capacidad FROM tbl_curso");
    }

    $cursos = $stmt->fetchAll();

    foreach ($cursos as &$c) {
        $c['codigo_curso'] = (int)$c['codigo_curso'];
        $c['idCarrera'] = (int)$c['idCarrera'];
    }

    echo json_encode($cursos);
} catch (Exception $e) {
    header("HTTP/1.1 500 Internal Server Error");
    echo json_encode(["error" => "Error al obtener catálogo de cursos: " . $e->getMessage()]);
}
?>