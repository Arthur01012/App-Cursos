package com.example.app_cursos.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_cursos.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regitro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun mostrarSelectorDeFecha(campoFecha: EditText) {
        val calendario = Calendar.getInstance()
        val anioActual = calendario.get(Calendar.YEAR)
        val mesActual = calendario.get(Calendar.MONTH)
        val diaActual = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Aplica el formato exacto que espera tu base de datos MariaDB (YYYY-MM-DD)
                val fechaFormateada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                campoFecha.setText(fechaFormateada)
            },
            anioActual,
            mesActual,
            diaActual
        )

        datePickerDialog.show()
    }
}