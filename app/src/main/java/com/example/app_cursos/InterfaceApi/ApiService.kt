package com.example.app_cursos.InterfaceApi

import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataCursos
import com.example.app_cursos.Modelo.dataAlumno
import com.example.app_cursos.Modelo.dataInscripcion
import com.example.app_cursos.Modelo.dataRespuestaInscripcion
import com.example.app_cursos.Modelo.dataRegistro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(value = "filtrar_carrera.php")
    suspend fun obtenerCarrera(): List<dataCarrera>

    @GET("obtener_carrera.php")
    suspend fun obtenerCursos(@Query("idCarrera") idCarrera: Int): List<dataCursos>

    @GET("obtener_alumno.php")
    fun obtenerAlumno(@Query("matricula") matricula: String): Call<dataAlumno>

    @GET("obtener_registros.php")
    suspend fun obtenerRegistros(@Query("idCarrera") idCarrera: Int): List<dataRegistro>

    @POST("inscribir.php")
    fun inscribirAlumno(@Body inscripcion: dataInscripcion): Call<dataRespuestaInscripcion>
}
