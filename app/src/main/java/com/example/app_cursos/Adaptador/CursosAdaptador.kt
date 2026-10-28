package com.example.app_cursos.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.material3.Button
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_cursos.Modelo.dataCursos
import com.example.app_cursos.R
import com.example.app_cursos.Vista.MainActivity

class CursosAdaptador(
    private val activity: MainActivity,
    private var lista: List<dataCursos>
) : RecyclerView.Adapter<CursosAdaptador.CursoViewHolder>() {
    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtCarrera)
        val txtSalon: TextView = itemView.findViewById(R.id.txtPrecio)
        val imgCurso: ImageView = itemView.findViewById(R.id.imgCarrera)

        val btnDetalle: Button = itemView.findViewById(R.id.btnVerDetalle)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = lista[position]
        holder.txtNombre.text = curso.tituloCurso
        holder.txtSalon.text = curso.espacio

        Glide.with(holder.itemView.context)
            .load("http://10.190.44.235/sistema_cursos/" + curso.rutaImgGrid)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgCurso)

        holder.btnDetalle.SetOnClickListener {
            activity.seleccionarCurso(curso)
        }

        holder.itemView.findViewById<View>(R.id.btnRegistrarse).setOnClickListener {
            activity.seleccionarCurso(curso)
        }
    }
    override fun getItemCount() = lista.size

    fun actualizarLista(nuevaLista: List<dataCursos>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}