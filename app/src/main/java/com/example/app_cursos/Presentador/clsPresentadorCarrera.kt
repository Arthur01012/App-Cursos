package com.example.app_cursos.Presentador

import com.example.app_cursos.Modelo.clsCarrerasModelo
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPresentadorCarrera(val vista: MainActivity)  {

    private val modelo = clsCarrerasModelo()
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
            //Si la categoria.id es 0, la API trae todos; si es >0 filtrará por esa categoria

        }
    }

}