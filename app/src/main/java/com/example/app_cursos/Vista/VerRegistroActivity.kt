package com.example.app_cursos.Vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_cursos.Adaptador.RegistrosAdaptador
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataRegistro
import com.example.app_cursos.Presentador.clsPresentadorRegistros
import com.example.app_cursos.R
import com.google.android.material.button.MaterialButton

class VerRegistroActivity : AppCompatActivity() {
    private lateinit var presenter: clsPresentadorRegistros
    private lateinit var spinner: Spinner
    private lateinit var recyclerRegistros: RecyclerView
    private lateinit var adapterRegistros: RegistrosAdaptador
    private lateinit var txtTotal: TextView
    private lateinit var btnVolver: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registros)

        spinner = findViewById(R.id.spnCarrerasRegistros)
        recyclerRegistros = findViewById(R.id.rvRegistros)
        txtTotal = findViewById(R.id.txtTotalInscritos)
        btnVolver = findViewById(R.id.btnVolverCatalogo)

        recyclerRegistros.layoutManager = LinearLayoutManager(this)
        adapterRegistros = RegistrosAdaptador(emptyList())
        recyclerRegistros.adapter = adapterRegistros

        presenter = clsPresentadorRegistros(this)
        configurarListeners()
        presenter.iniciar()
    }

    private fun configurarListeners() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.seleccionarCarrera(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnVolver.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                finish()
            }
        })
    }

    fun mostrarCarreras(carreras: List<dataCarrera>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun mostrarRegistros(registros: List<dataRegistro>) {
        adapterRegistros.actualizarLista(registros)
    }

    fun mostrarTotal(total: Int) {
        txtTotal.text = "Total: $total"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
