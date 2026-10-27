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
            val mensaje = try {
                cuerpo?.let { JSONObject(it).optString("mensaje").takeIf(String::isNotBlank) }
            } catch (_: JSONException) {
                null
            }

            if (mensaje != null) {
                return mensaje
            }

            return "Error del servidor (${error.code()})."
        }

        return "Error al registrar la inscripción: ${error.localizedMessage ?: "Error desconocido"}"
    }
}