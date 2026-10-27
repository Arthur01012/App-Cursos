package com.example.app_cursos.modelo

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
@Serializable
class dataInscripcion (
    @SerialName("codigo_curso") val codigoCurso: String,
    @SerialName("matricula") val matricula: String,
    @SerialName("correo") val correo: String,
    @SerialName("fecha_registro") val fechaRegistro: String
)
@Serializable
data class dataRespuestaInscripcion(
    @SerialName("exito") val exito: Boolean,
    @SerialName("mensaje") val mensaje: String,
    @SerialName("id_inscripcion") val idInscripcion: Int? = null
)
