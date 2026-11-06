package com.example.app_cursos.Vista

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_cursos.Modelo.RetrofitCliente
import com.example.app_cursos.R
import com.example.app_cursos.Modelo.dataAlumno
import com.example.app_cursos.Modelo.dataInscripcion
import com.example.app_cursos.Presentador.clsPresentadorInscripcion
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var txtNombre: TextView
    private lateinit var imgCurso: ImageView
    private lateinit var txtMatricula: EditText
    private lateinit var txtFecha: TextView
    private lateinit var txtCorreoInstitucional: EditText
    private lateinit var presenter: clsPresentadorInscripcion
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: Button

    private var codigoCurso:String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regitro)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main),
            object : OnApplyWindowInsetsListener {
                override fun onApplyWindowInsets(view: View, insets: WindowInsetsCompat): WindowInsetsCompat {
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    view.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom)
                    return insets
                }
            }
        )

        txtNombre = findViewById(R.id.txtNombreCurso)

        imgCurso = findViewById(R.id.imgCurso)
        txtMatricula = findViewById(R.id.etMatricula)
        txtFecha = findViewById(R.id.etFecha)
        txtCorreoInstitucional = findViewById(R.id.etCorreo)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnCancelar = findViewById(R.id.btnCancel)

        codigoCurso = intent.getStringExtra("CODIGO_CURSO" )?:""
        val nombreCurso = intent.getStringExtra("NOMBRE_CURSO")?:""
        val urlImagen = intent.getStringExtra("URL_IMAGEN")?:""

        txtNombre.text = nombreCurso

        presenter = clsPresentadorInscripcion(this)

        val baseUrl = "http://10.234.225.194/sistema_cursos/"
        Glide.with(this)
            .load(baseUrl + urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(imgCurso)
        txtMatricula.setOnEditorActionListener(this::ejecutarBuscarAlumno)

        btnConfirmar.setOnClickListener(this::ejecutarRegistro)
        btnCancelar.setOnClickListener(this::ejecutarCancelar)
    }
    fun ejecutarBuscarAlumno(v: TextView?, actionId: Int, event: KeyEvent?): Boolean
    {
        if (actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            val matricula = txtMatricula.text.toString().trim()
            presenter.buscarAlumno(matricula)
            return true
        }
        return false
    }

    fun ejecutarCancelar(v: View)
    {
        finish()
    }
    fun ejecutarRegistro(v: View)
    {
        presenter.registrar(codigoCurso, txtMatricula.text.toString(), txtFecha.text.toString())
    }
    fun insertarCorreo(correo: String)
    {
        txtCorreoInstitucional.setText(correo)
    }
    fun insertarFecha(fecha: String)
    {
        txtFecha.setText(fecha)
    }
    fun limpiar()
    {
        txtCorreoInstitucional.setText("")
        txtFecha.setText("")
    }
    fun registroExitoso(mensaje: String)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        finish()
    }
    fun mostrarMensaje(mensaje: String)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    fun mostrarError(mensaje: String)
    {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}