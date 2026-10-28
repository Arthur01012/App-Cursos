<?php

header("Content-Type: application/json; charset=UTF-8");

require_once "config/conexion.php";

$idCarrera = isset($_GET['idCarrera']) ? intval($_GET['idCarrera']) : 0;

try {

    if ($idCarrera > 0) {

        $stmt = $pdo->prepare("
            SELECT
                c.codigo_curso,
                c.idCarrera,
                c.titulo_curso,
                c.descripcion,
                c.espacio,
                c.total_horas,
                c.dias_atencion,
                c.horas_atencion,
                c.ruta_img_grid,
                c.capacidad,
                COUNT(i.id_inscripcion) AS inscritos
            FROM tbl_curso AS c
            LEFT JOIN tbl_inscripciones AS i
                ON c.codigo_curso = i.codigo_curso
            WHERE c.idCarrera = ?
            GROUP BY
                c.codigo_curso,
                c.idCarrera,
                c.titulo_curso,
                c.descripcion,
                c.espacio,
                c.total_horas,
                c.dias_atencion,
                c.horas_atencion,
                c.ruta_img_grid,
                c.capacidad
        ");

        $stmt->execute([$idCarrera]);

    } else {

        $stmt = $pdo->query("
            SELECT
                c.codigo_curso,
                c.idCarrera,
                c.titulo_curso,
                c.descripcion,
                c.espacio,
                c.total_horas,
                c.dias_atencion,
                c.horas_atencion,
                c.ruta_img_grid,
                c.capacidad,
                COUNT(i.id_inscripcion) AS inscritos
            FROM tbl_curso AS c
            LEFT JOIN tbl_inscripciones AS i
                ON c.codigo_curso = i.codigo_curso
            GROUP BY
                c.codigo_curso,
                c.idCarrera,
                c.titulo_curso,
                c.descripcion,
                c.espacio,
                c.total_horas,
                c.dias_atencion,
                c.horas_atencion,
                c.ruta_img_grid,
                c.capacidad
        ");
    }

    $cursos = $stmt->fetchAll(PDO::FETCH_ASSOC);

    foreach ($cursos as &$c) {
        $c['idCarrera'] = (int)$c['idCarrera'];
        $c['capacidad'] = (int)$c['capacidad'];
        $c['inscritos'] = (int)$c['inscritos'];
    }

    echo json_encode($cursos);

} catch (Exception $e) {

    header("HTTP/1.1 500 Internal Server Error");

    echo json_encode([
        "error" => "Error al obtener catálogo de cursos: " . $e->getMessage()
    ]);
}

?>
