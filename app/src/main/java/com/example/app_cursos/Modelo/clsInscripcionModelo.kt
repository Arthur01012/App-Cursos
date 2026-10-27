package com.example.app_cursos.modelo

import com.example.app_cursos.Modelo.RetrofitCliente
import com.example.app_cursos.Modelo.dataInscripcion
import com.example.app_cursos.Modelo.dataRespuestaInscripcion
class clsInscripcionModelo {
    suspend fun inscribirAlumno(solicitud: dataInscripcion): dataRespuestaInscripcion {
        return RetrofitCliente.apiService.inscribirAlumno(solicitud)
    }
}