package com.example.app_cursos.InterfaceApi

import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataCursos
import com.example.app_cursos.Modelo.dataRegistro
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(value = "filtrar_carrera.php")
    suspend fun obtenerCarrera(): List<dataCarrera>

    @GET("obtener_carrera.php")
    suspend fun obtenerCursos(@Query("idCarrera") idCarrera: Int): List<dataCursos>

    @GET("obtener_registros.php")
    suspend fun obtenerRegistros(@Query("idCarrera") idCarrera: Int): List<dataRegistro>
}
