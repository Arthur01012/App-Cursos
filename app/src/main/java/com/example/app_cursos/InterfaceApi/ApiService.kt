package com.example.app_cursos.InterfaceApi

import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataCursos
import  com.example.app_cursos.modelo.dataInfoCurso
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(value = "filtrar_carrera.php")
    suspend fun obtenerCarrera(): List<dataCarrera>

    @GET("obtener_carrera.php")
    suspend fun obtenerCursos(@Query("idCarrera") idCarrera: Int): List<dataCursos>

    @GET("cargar_info_curso.php")
    suspend fun obtenerInfoCurso(@Query("codigo_curso") codigoCurso: String
    ): dataInfoCurso
}
