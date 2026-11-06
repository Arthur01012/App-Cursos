package com.example.app_cursos.Modelo

class clsCursosModelo {
    suspend fun obtenerCursos(idCarrera: Int): List<dataCursos> {
        return RetrofitCliente.apiService.obtenerCursos(idCarrera)
    }
}