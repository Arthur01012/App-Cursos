package com.example.app_cursos.modelo

import com.example.app_cursos.Modelo.RetrofitCliente
import retrofit2.Call

class clsInscripcionModelo {
    fun obtenerAlumno(matricula: String): Call<dataAlumno> {
        return RetrofitCliente.apiService.obtenerAlumno(matricula)
    }

    fun inscribirAlumno(solicitud: dataInscripcion): Call<dataRespuestaInscripcion> {
        return RetrofitCliente.apiService.inscribirAlumno(solicitud)
    }
}