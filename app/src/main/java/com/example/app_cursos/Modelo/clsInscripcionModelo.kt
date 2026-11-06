package com.example.app_cursos.Modelo

class clsInscripcionModelo {
    suspend fun buscarAlumno(matricula:String):dataAlumno
    {
        return RetrofitCliente.apiService.obtenerAlumno(matricula)
    }
    suspend fun insertarInscripcion(codigocurso: String,matricula:String,fecha_registro: String):dataRespuestaInscripcion
    {
        return RetrofitCliente.apiService.registrarInscripcion(codigocurso,matricula,fecha_registro)
    }
}