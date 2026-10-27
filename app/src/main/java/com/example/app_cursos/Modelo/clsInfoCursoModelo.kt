package com.example.app_cursos.modelo

import com.example.app_cursos.Modelo.RetrofitCliente
import com.example.app_cursos.Modelo.dataCursos

class clsInfoCursoModelo {
    suspend fun obtenerInfoCurso(codigoCurso: String): dataCursos {
        return RetrofitCliente.apiService.obtenerInfoCurso(codigoCurso)
    }
}