package com.example.app_cursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataInsertarRegistro(
    @SerialName("codigo_curso") val codigo:String = "",
    @SerialName("mensaje") val mensaje: String = ""
)
