package com.example.app_cursos.Presentador

import com.example.app_cursos.Modelo.clsCursosModelo
import com.example.app_cursos.Modelo.dataImagenesCurso
import com.example.app_cursos.Vista.DetalleCursoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsDetalleCursoPresentador(private val vista: DetalleCursoActivity) {

    private val modelo = clsCursosModelo()

    fun cargarDetalle(idCurso: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val listaImagenes: List<dataImagenesCurso> = modelo.obtenerDetalleCurso(idCurso)

            } catch (e: Exception) {
                vista.mostrarError("Error al cargar detalle: " + e.localizedMessage)
            }
        }
    }


}