package com.example.app_cursos.Modelo

class clsCarrerasModelo {
    suspend fun obtenerCarreras(): List<dataCarrera> {
        val datos = RetrofitCliente.apiService.obtenerCarrera() // Aquí hace el pedido a la BD

        val listaFinal = mutableListOf(dataCarrera(0, "Todas las carreras"))
        listaFinal.addAll(datos)

        return listaFinal // Retorna la lista directamente
    }
}
