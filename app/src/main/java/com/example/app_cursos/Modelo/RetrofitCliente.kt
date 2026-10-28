package com.example.app_cursos.Modelo

import com.example.app_cursos.InterfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.lazy
object  RetrofitCliente {
    private const val BASE_URL = "http://192.168.1.89/sistema_cursos/"
    private val jsonConfig = Json{
        ignoreUnknownKeys=true
        coerceInputValues=true
        isLenient=true
    }
    val apiService: ApiService by lazy {
        val contenType="application/json".toMediaType()
            Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contenType))
            .build()
            .create(ApiService::class.java)
    }
    fun urlCompleta(ruta: String): String = BASE_URL + ruta.trimStart('/')
}

