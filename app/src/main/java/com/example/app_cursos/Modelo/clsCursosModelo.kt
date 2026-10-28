package com.example.app_cursos.Modelo

import com.example.app_cursos.Modelo.dataCursos

class clsCursosModelo {
    suspend fun obtenerCursos(idCarrera: Int): List<dataCursos> {
        return RetrofitCliente.apiService.obtenerCursos(idCarrera)
    }
}