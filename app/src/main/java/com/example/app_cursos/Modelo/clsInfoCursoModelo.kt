package com.example.app_cursos.modelo

class clsInfoCursoModelo {
    suspend fun obtenerInfoCurso(codigoCurso: String):dataInfoCurso{
        return RetrofitCliente.apiService.obtenerInfoCurso(codigoCurso)
    }
}