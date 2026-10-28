package com.example.app_cursos.presentador

import com.example.app_cursos.modelo.clsInscripcionModelo
import com.example.app_cursos.modelo.dataInscripcion
import com.example.app_cursos.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class clsPresentadorInscripcion(private val vista: RegistroActivity) {
    private val modelo = clsInscripcionModelo()

    fun inscribir(solicitud: dataInscripcion) {
        CoroutineScope(Dispatchers.Main).launch {
            vista.mostrarCargando(true)
            try {
                val respuesta = modelo.inscribirAlumno(solicitud)
                if (respuesta.exito) {
                    vista.mostrarResultadoInscripcion(respuesta.mensaje)
                } else {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                vista.mostrarError(obtenerMensajeError(e))
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }

    private fun obtenerMensajeError(error: Exception): String {
        if (error is HttpException) {
            val cuerpo = try {
                error.response()?.errorBody()?.string()
            } catch (_: IOException) {
                null
            }
            val respuestaError = try {
                cuerpo?.let(::JSONObject)
            } catch (_: JSONException) {
                null
            }

            val mensaje = respuestaError
                ?.optString("mensaje")
                ?.takeIf(String::isNotBlank)
            if (mensaje != null) {
                val codigoCursoRecibido = respuestaError
                    .optString("codigo_curso_recibido")
                    .takeIf(String::isNotBlank)
                if (codigoCursoRecibido != null) {
                    return "$mensaje Código recibido: $codigoCursoRecibido."
                }

                val camposFaltantes = respuestaError.optJSONArray("campos_faltantes")
                if (camposFaltantes != null && camposFaltantes.length() > 0) {
                    val nombres = mapOf(
                        "codigo_curso" to "código del curso",
                        "matricula" to "matrícula",
                        "correo" to "correo",
                        "fecha_registro" to "fecha de registro"
                    )
                    val faltantes = (0 until camposFaltantes.length())
                        .map { camposFaltantes.optString(it) }
                        .map { nombres[it] ?: it }
                    return "$mensaje Campos que no llegaron: ${faltantes.joinToString()}."
                }
                return mensaje
            }

            return "Error del servidor (${error.code()})."
        }

        return "Error al registrar la inscripción: ${error.localizedMessage ?: "Error desconocido"}"
    }
}