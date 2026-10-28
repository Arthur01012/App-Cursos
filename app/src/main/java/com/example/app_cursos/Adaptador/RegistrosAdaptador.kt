package com.example.app_cursos.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_cursos.Modelo.RetrofitCliente
import com.example.app_cursos.Modelo.dataRegistro
import com.example.app_cursos.R

class RegistrosAdaptador(
    private var lista: List<dataRegistro>
) : RecyclerView.Adapter<RegistrosAdaptador.RegistroViewHolder>() {

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTituloRegistro)
        val txtInscritos: TextView = itemView.findViewById(R.id.txtInscritos)
        val txtCapacidad: TextView = itemView.findViewById(R.id.txtCapacidad)
        val imgCurso: ImageView = itemView.findViewById(R.id.imgCursoRegistro)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarInscritos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro, parent, false)
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = lista[position]
        holder.txtTitulo.text = registro.tituloCurso
        holder.txtInscritos.text = "${registro.inscritos} inscritos"
        holder.txtCapacidad.text = "/ ${registro.capacidad} total"
        holder.progressBar.max = registro.capacidad
        holder.progressBar.progress = registro.inscritos

        if (registro.rutaImgGrid.isNullOrBlank()) {
            holder.imgCurso.setImageResource(android.R.drawable.ic_menu_gallery)
        } else {
            Glide.with(holder.itemView.context)
                .load(RetrofitCliente.urlCompleta(registro.rutaImgGrid))
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imgCurso)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun actualizarLista(nuevaLista: List<dataRegistro>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
