package com.example.app_cursos.presentador

import com.example.app_cursos.modelo.clsInfoCursoModelo
import com.example.app_cursos.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPresentadorInfoCurso(val vista: RegistroActivity) {
    private val modelo = clsInfoCursoModelo()

    fun cargarCurso(codigoCurso: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Solicitar al modelo los datos del curso y mostrarlos en la vista.
                val curso = modelo.obtenerInfoCurso(codigoCurso)
                vista.mostrarCurso(curso)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar el curso: " + e.localizedMessage)
            }
        }
    }
}