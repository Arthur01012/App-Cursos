package com.example.app_cursos.presentador

import com.example.app_cursos.Vista.RegistroActivity
import com.example.app_cursos.modelo.clsInscripcionModelo
import com.example.app_cursos.modelo.dataAlumno
import com.example.app_cursos.modelo.dataInscripcion
import com.example.app_cursos.modelo.dataRespuestaInscripcion
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class clsPresentadorInscripcion(private val vista: RegistroActivity) {
    private val modelo = clsInscripcionModelo()

    fun buscarAlumno(matricula: String) {
        vista.mostrarBuscandoAlumno(true)
        modelo.obtenerAlumno(matricula).enqueue(object : Callback<dataAlumno> {
            override fun onResponse(
                llamada: Call<dataAlumno>,
                respuesta: Response<dataAlumno>
            ) {
                vista.mostrarBuscandoAlumno(false)
                if (respuesta.isSuccessful) {
                    val alumno = respuesta.body()
                    if (alumno == null || alumno.correo.isBlank()) {
                        vista.mostrarError("La API no devolvió un correo institucional para esta matrícula.")
                    } else {
                        vista.mostrarDatosAlumno(alumno)
                    }
                } else {
                    vista.mostrarError(leerMensajeError(respuesta))
                }
            }

            override fun onFailure(llamada: Call<dataAlumno>, error: Throwable) {
                vista.mostrarBuscandoAlumno(false)
                vista.mostrarError(
                    "No se pudo consultar la matrícula: ${error.localizedMessage ?: "error de conexión"}"
                )
            }
        })
    }

    fun inscribir(solicitud: dataInscripcion) {
        vista.mostrarCargando(true)
        modelo.inscribirAlumno(solicitud).enqueue(object : Callback<dataRespuestaInscripcion> {
            override fun onResponse(
                llamada: Call<dataRespuestaInscripcion>,
                respuesta: Response<dataRespuestaInscripcion>
            ) {
                vista.mostrarCargando(false)
                if (respuesta.isSuccessful) {
                    val resultado = respuesta.body()
                    if (resultado != null && resultado.exito) {
                        vista.mostrarResultadoInscripcion(resultado.mensaje)
                    } else {
                        vista.mostrarError(
                            resultado?.mensaje ?: "La API no devolvió la confirmación de inscripción."
                        )
                    }
                } else {
                    vista.mostrarError(leerMensajeError(respuesta))
                }
            }

            override fun onFailure(
                llamada: Call<dataRespuestaInscripcion>,
                error: Throwable
            ) {
                vista.mostrarCargando(false)
                vista.mostrarError(
                    "No se pudo enviar la inscripción: ${error.localizedMessage ?: "error de conexión"}"
                )
            }
        })
    }

    private fun leerMensajeError(respuesta: Response<*>): String {
        val cuerpo = try {
            respuesta.errorBody()?.string()
        } catch (error: IOException) {
            return "Error del servidor (${respuesta.code()}); no se pudo leer el detalle."
        }

        if (cuerpo.isNullOrBlank()) {
            return "Error del servidor (${respuesta.code()})."
        }

        return try {
            val json = JSONObject(cuerpo)
            val mensaje = json.optString("mensaje")
            if (mensaje.isNotBlank()) {
                mensaje
            } else {
                "Error del servidor (${respuesta.code()})."
            }
        } catch (error: JSONException) {
            "Error del servidor (${respuesta.code()})."
        }
    }
}
