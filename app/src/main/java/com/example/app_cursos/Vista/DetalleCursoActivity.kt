package com.example.app_cursos.Vista

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_cursos.R

class DetalleCursoActivity : AppCompatActivity() {
    private lateinit var txtCodidoCurso : TextView
    private lateinit var txtNombreCuros: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtEspacio: TextView
    private lateinit var txtTotalHoras: TextView
    private lateinit var txtDiasAtencion: TextView
    private lateinit var txtHorasAtencion: TextView
    private lateinit var imgCurso: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_curso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgCurso = findViewById(R.id.imgCursoDetalle)
        txtCodidoCurso = findViewById(R.id.tvCodigoCurso)
        txtNombreCuros = findViewById(R.id.tvTituloCurso)
        txtDescripcion = findViewById(R.id.tvDescripcionCurso)
        txtEspacio = findViewById(R.id.tvEspacioCurso)
        txtTotalHoras = findViewById(R.id.tvTotalHorasCurso)
        txtDiasAtencion = findViewById(R.id.tvDiasCurso)
        txtHorasAtencion = findViewById(R.id.tvHorasCurso)

        val codigoCurso = intent.getStringExtra("CODIGO_CURSO") ?: ""
        val nombreCurso = intent.getStringExtra("NOMBRE_CURSO") ?: ""
        val descripcion = intent.getStringExtra("DESCRIPCION") ?: ""
        val espacio = intent.getStringExtra("ESPACIO") ?: ""
        val totalHoras = intent.getIntExtra("TOTAL_HORAS", 0)
        val diasAtencion = intent.getStringExtra("DIAS_ATENCION") ?: ""
        val horasAtencion = intent.getStringExtra("HORAS_ATENCION") ?: ""
        val urlImagen = intent.getStringExtra("URL_IMAGEN")?:""

        txtCodidoCurso.text = codigoCurso
        txtNombreCuros.text = nombreCurso
        txtDescripcion.text = descripcion
        txtEspacio.text = espacio
        txtTotalHoras.text = totalHoras.toString()
        txtDiasAtencion.text = diasAtencion
        txtHorasAtencion.text = horasAtencion

        val baseUrl = "http://10.234.225.194/sistema_cursos/"
        Glide.with(this)
            .load(baseUrl + urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(imgCurso)

    }
}