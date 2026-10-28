package com.example.app_cursos.Vista

import android.content.Intent
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
import com.example.app_cursos.Adaptador.CursosAdaptador
import com.example.app_cursos.Modelo.dataCarrera
import com.example.app_cursos.Modelo.dataCursos
import com.example.app_cursos.Presentador.clsPresentadorCarrera
import com.example.app_cursos.R
import com.example.app_cursos.Vista.RegistroActivity

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: clsPresentadorCarrera
    private lateinit var spinner : Spinner
    private lateinit var recyclerCursos : RecyclerView
    private lateinit var adapterCursos: CursosAdaptador


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
        recyclerCursos=findViewById(R.id.rvLista)

        recyclerCursos.layoutManager = GridLayoutManager (this,2)
        adapterCursos = CursosAdaptador(this, emptyList())
        presenter = clsPresentadorCarrera(this)
        recyclerCursos.adapter = adapterCursos

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
    fun mostrarCursos(cursos: List<dataCursos>) {
        adapterCursos.actualizarLista(cursos)
    }
    fun seleccionarCurso(curso: dataCursos) {
        presenter.seleccionarCursosetalle(curso)
    }
    fun abrirRegistroCurso(curso: dataCursos) {
        val intent = Intent(this, RegistroActivity::class.java)
            .putExtra(RegistroActivity.EXTRA_CODIGO_CURSO, curso.codigoCurso)
            .putExtra(RegistroActivity.EXTRA_TITULO_CURSO, curso.tituloCurso)
            .putExtra(RegistroActivity.EXTRA_RUTA_IMAGEN, curso.rutaImgGrid)
        startActivity(intent)
    }

    fun abrirDetalleCurso(
        id:Int,codigoCurso: String, nombreCurso: String, descripcion: String,
        espacio: String, totalHoras: Int, diasAtencion: String, horasAtencion: String,
        rutaImagen: String?
    ) {
        val intent = Intent(this, DetalleCursoActivity::class.java)
        intent.putExtra("ID_CURSO",id)
        intent.putExtra("CODIGO_CURSO", codigoCurso)
        intent.putExtra("NOMBRE_CURSO", nombreCurso)
        intent.putExtra("DESCRIPCION", descripcion)
        intent.putExtra("ESPACIO", espacio)
        intent.putExtra("TOTAL_HORAS", totalHoras)
        intent.putExtra("DIAS_ATENCION", diasAtencion)
        intent.putExtra("HORAS_ATENCION", horasAtencion)
        intent.putExtra("RUTA_IMAGEN", rutaImagen)
        startActivity(intent)
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