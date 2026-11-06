package com.example.app_cursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataAlumno(
    @SerialName("exito") val exito: Boolean=false,
    @SerialName("mensaje")val mensaje: String ="",
    @SerialName("matricula") val matricula: String = "",
    @SerialName("correo") val correo: String = ""
)