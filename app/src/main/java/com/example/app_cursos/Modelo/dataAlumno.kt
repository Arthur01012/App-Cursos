package com.example.app_cursos.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataAlumno(
    @SerialName("matricula") val matricula: String,
    @SerialName("correo") val correo: String
)
