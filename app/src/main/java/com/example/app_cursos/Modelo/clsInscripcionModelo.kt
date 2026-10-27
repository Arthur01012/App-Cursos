package com.example.app_cursos.modelo

import com.example.app_cursos.Modelo.RetrofitCliente

class clsInscripcionModelo {
    suspend fun inscribirAlumno(solicitud: dataInscripcion): dataRespuestaInscripcion {
    return RetrofitCliente.apiService.inscribirAlumno(solicitud)
}

}