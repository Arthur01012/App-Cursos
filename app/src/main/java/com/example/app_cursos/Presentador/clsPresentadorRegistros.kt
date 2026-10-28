package com.example.app_cursos.Presentador

import com.example.app_cursos.Modelo.clsCarrerasModelo
import com.example.app_cursos.Modelo.clsRegistrosModelo
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataRegistro
import com.example.app_cursos.Vista.VerRegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPresentadorRegistros(val vista: VerRegistroActivity) {
    private val modeloCarreras = clsCarrerasModelo()
    private val modeloRegistros = clsRegistrosModelo()
    private var listaCarreras: List<dataCarrera> = emptyList()
    fun iniciar(){

        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaCarreras = modeloCarreras.obtenerCarreras()
                vista.mostrarCarreras(listaCarreras)

                if (listaCarreras.isNotEmpty()) {
                 cargarRegistros(listaCarreras[0].idCarrera)
                }
            }catch (e: Exception){
                vista.mostrarError("Error al cargar carreras: " + e.localizedMessage)
            }
        }
    }
    fun seleccionarCarrera(position: Int){
        if (position >= 0 && position < listaCarreras.size){
            val carrera = listaCarreras[position]
            cargarRegistros(carrera.idCarrera)
        }
    }
    private fun cargarRegistros(idCarrera: Int){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val registros = modeloRegistros.obtenerRegistros(idCarrera)
                val totalInscritos = registros.sumOf { it.inscritos }
                vista.mostrarRegistros(registros)
                vista.mostrarTotal(totalInscritos)
            }catch (e: Exception){
                vista.mostrarError("Error al cargar registros: " + e.localizedMessage)
            }
        }
    }
}