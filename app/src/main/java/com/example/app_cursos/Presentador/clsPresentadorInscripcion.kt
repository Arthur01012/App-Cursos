package com.example.app_cursos.Presentador

import com.example.app_cursos.Vista.RegistroActivity
import com.example.app_cursos.Modelo.clsInscripcionModelo
import com.example.app_cursos.Modelo.dataAlumno
import com.example.app_cursos.Modelo.dataInscripcion
import com.example.app_cursos.Modelo.dataRespuestaInscripcion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                val alumno = respuesta.body()

                if (respuesta.isSuccessful && alumno != null) {
                    vista.mostrarDatosAlumno(alumno)
                    return
                }

                vista.mostrarError("No se encontró la matrícula.")
            }

            override fun onFailure(llamada: Call<dataAlumno>, error: Throwable) {
                vista.mostrarBuscandoAlumno(false)
                vista.mostrarError("No se pudo consultar la matrícula.")
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
                val resultado = respuesta.body()

                if (respuesta.isSuccessful && resultado != null && resultado.exito) {
                    vista.mostrarResultadoInscripcion(resultado.mensaje)
                    return
                }

                vista.mostrarError("No se pudo confirmar la inscripción.")
            }

            override fun onFailure(
                llamada: Call<dataRespuestaInscripcion>,
                error: Throwable
            ) {
                vista.mostrarCargando(false)
                vista.mostrarError("No se pudo enviar la inscripción.")
            }
        })
    }
}
