package com.example.app_cursos.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.app_cursos.Presentador.clsDetalleCursoPresentador
import com.example.app_cursos.R

class DetalleCursoActivity : AppCompatActivity() {
    private lateinit var presenter: clsDetalleCursoPresentador
    private lateinit var txtCodidoCurso : TextView
    private lateinit var txtNombreCuros: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtEspacio: TextView
    private lateinit var txtTotalHoras: TextView
    private lateinit var txtDiasAtencion: TextView
    private lateinit var txtHorasAtencion: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_curso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtCodidoCurso = findViewById(R.id.txtdetalleCodigo)
        txtNombreCuros = findViewById(R.id.txtdetalleCurso)
        txtDescripcion = findViewById(R.id.txtdetalleDescripcion)
        txtEspacio = findViewById(R.id.txtdetalleEspacio)
        txtTotalHoras = findViewById(R.id.txtdetalleHoras)
        txtDiasAtencion = findViewById(R.id.txtdetalleDiasAtencion)
        txtHorasAtencion = findViewById(R.id.txtdetalleHorasAtencion)

        val id = intent.getIntExtra("ID_CURSO",0)
        val codigoCurso = intent.getStringExtra("CODIGO_CURSO")?:""
        val nombreCurso = intent.getStringExtra("NOMBRE_CURSO")?:""
        val descripcion = intent.getStringExtra("DESCRIPCION")?:""
        val espacio = intent.getStringExtra("ESPACIO")?:""
        val totalHoras = intent.getStringExtra("TOTAL_HORAS")?:""
        val diasAtencion = intent.getStringExtra("DIAS_ATENCION")?:""
        val horasAtencion = intent.getStringExtra("HORAS_ATENCION")?:""

        txtCodidoCurso.text = codigoCurso
        txtNombreCuros.text = nombreCurso
        txtDescripcion.text = descripcion
        txtEspacio.text = espacio
        txtTotalHoras.text = totalHoras + " Horas"
        txtDiasAtencion.text = diasAtencion
        txtHorasAtencion.text = "(" + horasAtencion +"hrs)"

        presenter = clsDetalleCursoPresentador(this)
        if(id!=0)
        {
            presenter.cargarDetalle(id)
        }
    }
    fun mostrarError(mensaje: String)
    {
        Toast.makeText(this,mensaje, Toast.LENGTH_LONG).show()
    }
}