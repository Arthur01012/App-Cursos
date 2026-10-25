package com.example.app_cursos.InterfaceApi

import com.example.app_cursos.Modelo.dataCarrera
import retrofit2.http.GET

interface ApiService {
    @GET(value = "filtrar_carrera.php")
    suspend fun obtenerCarrera(): List<dataCarrera>
}
