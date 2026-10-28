package com.example.app_cursos.Vista

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_cursos.Modelo.RetrofitCliente
import com.example.app_cursos.Modelo.dataCursos
import com.example.app_cursos.presentador.clsPresentadorInscripcion
import com.example.app_cursos.R
import com.example.app_cursos.modelo.dataInscripcion
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var presenterInscripcion: clsPresentadorInscripcion
    private lateinit var txtNombreCurso: TextView
    private lateinit var imgCurso: ImageView
    private lateinit var etMatricula: EditText
    private lateinit var etFecha: EditText
    private lateinit var etCorreo: EditText
    private lateinit var btnConfirmar: com.google.android.material.button.MaterialButton
    private var codigoCurso: String = ""
    private var fechaSeleccionada: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regitro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNombreCurso = findViewById(R.id.txtNombreCurso)
        imgCurso = findViewById(R.id.imgCurso)
        etMatricula = findViewById(R.id.etMatricula)
        etFecha = findViewById(R.id.etFecha)
        etCorreo = findViewById(R.id.etCorreo)
        btnConfirmar = findViewById(R.id.btnConfirmar)

        codigoCurso = intent.getStringExtra(EXTRA_CODIGO_CURSO).orEmpty()
        configurarListeners()

        presenterInscripcion = clsPresentadorInscripcion(this)
        mostrarCursoSeleccionado(
            tituloCurso = intent.getStringExtra(EXTRA_TITULO_CURSO).orEmpty(),
            rutaImagen = intent.getStringExtra(EXTRA_RUTA_IMAGEN)
        )
    }

    private fun configurarListeners() {
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
            .setOnClickListener { finish() }
        etFecha.setOnClickListener { mostrarSelectorDeFecha() }
        findViewById<ImageView>(R.id.btnCalendar).setOnClickListener { mostrarSelectorDeFecha() }
        btnConfirmar.setOnClickListener { enviarInscripcion() }
    }

    private fun mostrarCursoSeleccionado(tituloCurso: String, rutaImagen: String?) {
        txtNombreCurso.text = tituloCurso.ifBlank { "Curso seleccionado" }
        if (rutaImagen.isNullOrBlank()) {
            imgCurso.setImageResource(android.R.drawable.ic_menu_gallery)
        } else {
            Glide.with(this)
                .load(RetrofitCliente.urlCompleta(rutaImagen))
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(imgCurso)
        }

        btnConfirmar.isEnabled = true
    }

    fun mostrarCurso(curso: dataCursos) {
        mostrarCursoSeleccionado(curso.tituloCurso, curso.rutaImgGrid)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun mostrarCargando(cargando: Boolean) {
        btnConfirmar.isEnabled = !cargando
        btnConfirmar.text = if (cargando) "ENVIANDO..." else "CONFIRMAR INSCRIPCIÓN"
    }

    fun mostrarResultadoInscripcion(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun enviarInscripcion() {
        val matricula = etMatricula.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val fecha = etFecha.text.toString().trim()

        when {
            codigoCurso.isBlank() -> mostrarError("No se recibió el código del curso.")
            matricula.isBlank() -> {
                etMatricula.error = "Ingresa tu matrícula."
                etMatricula.requestFocus()
            }
            fecha.isBlank() -> {
                etFecha.error = "Selecciona la fecha de registro."
                etFecha.requestFocus()
            }
            correo.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                etCorreo.error = "Ingresa un correo válido."
                etCorreo.requestFocus()
            }
            else -> presenterInscripcion.inscribir(
                dataInscripcion(
                    codigoCurso = codigoCurso,
                    matricula = matricula,
                    correo = correo,
                    fechaRegistro = fecha
                )
            )
        }
    }

    private fun mostrarSelectorDeFecha() {
        val calendario = (fechaSeleccionada ?: Calendar.getInstance()).clone() as Calendar
        DatePickerDialog(
            this,
            { _, anio, mes, dia ->
                fechaSeleccionada = Calendar.getInstance().apply {
                    set(anio, mes, dia)
                }
                etFecha.setText(
                    String.format(Locale.US, "%04d-%02d-%02d", anio, mes + 1, dia)
                )
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    companion object {
        const val EXTRA_CODIGO_CURSO = "codigo_curso"
        const val EXTRA_TITULO_CURSO = "titulo_curso"
        const val EXTRA_RUTA_IMAGEN = "ruta_img_grid"
    }
}
