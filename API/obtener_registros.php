<?php
    $host = "localhost";
    $dbname = "sistema_cursos";
    $user = "root";
    $pass = "";

    $idCarrera = isset ($_GET['idCarrera']) ? intval($_GET['idCarrera']) :0;

    try {
        $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $user, $pass);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        if ($idCarrera > 0) {
            $sql = "SELECT c.codigo_curso, c.titulo_curso, c.ruta_img_grid. c.capacidad,
            COUNT (i.id_descripscion) as inscritos
            From tbl_curso c
            LEFT JOIN tbl_inscripciones i ON c.codigo_curso = i.codigo_curso
            WHERE c.id_carrera = :idCarrera
            GROUP BY c.codigo_curso";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam (':idCarrera', $idCarrera, PDO::PARAM_INT);
            $stmt->execute();
        } else {
            $sql = "SELECT c.codigo_curso, c.titulo_curso, c.ruta_img_grid, c.capacidad,
            COUNT(i.id_descripcion) as inscritos
            FROM tbl_curso c
            LEFT JOIN tbl_inscripciones i ON c.codigo_curso = i.codigo_curso
            GROUP BY c.codigo_curso";
            $stmt = $pdo->prepare($sql);
        }

        $stmt->execute();
        $resultados = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($resultados);

    }catch(PDOException $e) {
        http_response_code(500);
        echo json_encode(array("error" => "Error en la conexión a la base de datos: " . $e->getMessage()));
    }

?>