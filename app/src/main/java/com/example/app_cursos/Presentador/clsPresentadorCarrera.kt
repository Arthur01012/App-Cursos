package com.example.app_cursos.Presentador

import com.example.app_cursos.Modelo.clsCursosModelo

import com.example.app_cursos.Modelo.clsCarrerasModelo
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPresentadorCarrera(val vista: MainActivity)  {

    private val modelo = clsCarrerasModelo()
    private val modeloCursos = clsCursosModelo()
    private var listaCarreras: List<dataCarrera> = emptyList()

    fun iniciar()
    {
        //Se usa un Scope simple para no bloquear la interfaz
        CoroutineScope(Dispatchers.Main).launch {
            try{
                //Aqui se recibe el return directo del modelo
                //1.Cargar la lisra de categorias en el spinner
                listaCarreras = modelo.obtenerCarreras()
                vista.mostrarCarreras(listaCarreras)

                cargarCursos(0)

            }catch (e: Exception)
            {
                vista.mostrarError("Error:"+e.localizedMessage)
            }
        }
    }
    fun seleccionarCarrera(position:Int)
    {
        if(position >= 0 && position < listaCarreras.size)
        {
            val carrera = listaCarreras[position]
            cargarCursos(carrera.idCarrera)
        }
    }
    private fun cargarCursos(idCarrera: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cursos = modeloCursos.obtenerCursos(idCarrera)
                vista.mostrarCursos(cursos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar cursos: " + e.localizedMessage)
            }
        }
    }

}