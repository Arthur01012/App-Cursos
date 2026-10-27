package com.example.app_cursos.modelo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
class dataInfoCurso (
    @SerialName("codigo_curso") val codigoCurso: String,
    @SerialName("titulo_curso") val tituloCurso: String,
    @SerialName("ruta_img_grid") val rutaImgGrid: String? = null
)