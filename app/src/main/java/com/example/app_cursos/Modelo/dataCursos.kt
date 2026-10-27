package com.example.app_cursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCursos(
    @SerialName("codigo_curso") val codigoCurso: Int,
    @SerialName("idCarrera") val idCarrera: Int,
    @SerialName("titulo_curso") val tituloCurso: String,
    @SerialName("descripcion") val descripcion: String,
    @SerialName("espacio") val espacio: String,
    @SerialName("total_horas") val totalHoras: Int,
    @SerialName("dias_atencion") val diasAtencion: String,
    @SerialName("horas_atencion") val horasAtencion: String,
    @SerialName("ruta_img_grid") val rutaImgGrid: String? = null,
    @SerialName("capacidad") val capacidad: Int,
    @SerialName("ruta_img_bg")  val ruatImgbg: String? = null
)
