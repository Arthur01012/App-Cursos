package com.example.app_cursos.Presentador

import com.example.app_cursos.Modelo.clsCarrerasModelo
import com.example.app_cursos.Modelo.clsRegistrosModelo
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataRegistro
import com.example.app_cursos.Vista.VerRegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPresentadorRegistros(private val vista: VerRegistroActivity) {
    private val modeloCarreras = clsCarrerasModelo()
    private val modeloRegistros = clsRegistrosModelo()
    private var listaCarreras: List<dataCarrera> = emptyList()

    fun iniciar() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaCarreras = modeloCarreras.obtenerCarreras()
                vista.mostrarCarreras(listaCarreras)
                if (listaCarreras.isNotEmpty()) {
                    cargarRegistros(listaCarreras[0].idCarrera)
                }
            } catch (error: Exception) {
                vista.mostrarError(
                    "Error al cargar carreras: ${error.localizedMessage ?: "error desconocido"}"
                )
            }
        }
    }

    fun seleccionarCarrera(position: Int) {
        if (position >= 0 && position < listaCarreras.size) {
            cargarRegistros(listaCarreras[position].idCarrera)
        }
    }

    private fun cargarRegistros(idCarrera: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val registros = modeloRegistros.obtenerRegistros(idCarrera)
                var totalInscritos = 0
                for (registro in registros) {
                    totalInscritos += registro.inscritos
                }
                vista.mostrarRegistros(registros)
                vista.mostrarTotal(totalInscritos)
            } catch (error: Exception) {
                vista.mostrarError(
                    "Error al cargar registros: ${error.localizedMessage ?: "error desconocido"}"
                )
            }
        }
    }
}
