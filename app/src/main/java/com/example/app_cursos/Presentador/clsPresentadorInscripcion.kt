package com.example.app_cursos.Presentador

import com.example.app_cursos.Vista.RegistroActivity
import com.example.app_cursos.Modelo.clsInscripcionModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class clsPresentadorInscripcion(private val vista: RegistroActivity) {
    private val modelo = clsInscripcionModelo()

    fun buscarAlumno(matricula: String)
    {
        if (matricula.isEmpty())
        {
            vista.limpiar()
            vista.mostrarMensaje("La matrícula es obligatoria")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val alumno = modelo.buscarAlumno(matricula)
                if (alumno.exito)
                {
                    vista.insertarFecha(obtenerFechaHoy())
                    vista.insertarCorreo(alumno.correo)
                    vista.mostrarMensaje(alumno.mensaje)
                }
                else
                {
                    vista.limpiar()
                    vista.mostrarError(alumno.mensaje)
                }
            } catch (e: Exception)
            {
                vista.limpiar()
                vista.mostrarError("Error al buscar alumno: " + e.localizedMessage)
            }
        }
    }
    fun registrar(codigocurso: String, matricula: String, fecha: String)
    {
        if (matricula.isEmpty())
        {
            vista.mostrarError("Escribe la matrícula")
            return
        }
        if (fecha.isEmpty())
        {
            vista.mostrarError("Consulta la matrícula antes de confirmar")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.insertarInscripcion(codigocurso, matricula, fecha)
                if (respuesta.exito)
                {
                    vista.registroExitoso(respuesta.mensaje)
                }
                else
                {
                    vista.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception)
            {
                vista.mostrarError("No se pudo registrar la inscripción: " + e.localizedMessage)
            }
        }
    }

    private fun obtenerFechaHoy(): String
    {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }
}