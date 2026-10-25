package com.example.app_cursos.Vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Presentador.clsPresentadorCarrera
import com.example.app_cursos.R

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: clsPresentadorCarrera
    private lateinit var spinner : Spinner
    private lateinit var recyclerCarrera : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinner = findViewById(R.id.spnCarreras)
        recyclerCarrera=findViewById(R.id.rvLista)

        recyclerCarrera.layoutManager = GridLayoutManager (this,2)
        presenter = clsPresentadorCarrera(this)

        configurarListeners()
        presenter.iniciar()
    }
    private fun configurarListeners()
    {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
    }
    fun mostrarCarreras(carrera:List<dataCarrera>)
    {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,carrera)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
    fun mostrarMensajeExito(id:Int, nombre:String)
    {
        Toast.makeText(this,"ID: " + id.toString() + " -Seleccionaste:"+nombre, Toast.LENGTH_SHORT).show()
    }
    fun mostrarError(mensaje:String)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}