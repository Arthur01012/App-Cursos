package com.example.app_cursos.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataInscripcion(
    @SerialName("codigo_curso") val codigoCurso: String,
    @SerialName("matricula") val matricula: String,
    @SerialName("fecha_registro") val fechaRegistro: String
)

@Serializable
data class dataRespuestaInscripcion(
    @SerialName("exito") val exito: Boolean,
    @SerialName("mensaje") val mensaje: String,
    @SerialName("id_inscripcion") val idInscripcion: Int? = null
)