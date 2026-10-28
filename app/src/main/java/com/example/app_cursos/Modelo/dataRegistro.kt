package com.example.app_cursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistro(
    @SerialName("codigo_curso") val codigoCurso: String,
    @SerialName("titulo_curso") val tituloCurso: String,
    @SerialName("ruta_img_grid") val rutaImgGrid: String? = null,
    @SerialName("capacidad") val capacidad: Int,
    @SerialName("inscritos") val inscritos: Int
)