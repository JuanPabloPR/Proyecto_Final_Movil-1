package com.example.proyectofinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {

    private val entries = ArrayList<FormData>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var progressBar: ProgressBar
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val nameEt = findViewById<EditText>(R.id.etName)
        val emailEt = findViewById<EditText>(R.id.etEmail)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        progressBar = findViewById(R.id.progressBar)
        submitBtn = findViewById(R.id.btnSubmit)
        val listView = findViewById<ListView>(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        listView.adapter = adapter

        var selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(calendarView.date))
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month+1, year)
        }

        submitBtn.setOnClickListener {
            val name = nameEt.text.toString()
            val email = emailEt.text.toString()

            if (name.isBlank() || email.isBlank()) {
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Desea guardar la entrada?")
                .setPositiveButton("Sí") { _, _ ->
                    // Mostrar ProgressBar y deshabilitar botón
                    progressBar.visibility = View.VISIBLE
                    submitBtn.isEnabled = false

                    // Simular procesamiento (2 segundos)
                    Handler(Looper.getMainLooper()).postDelayed({
                        // Guardar datos
                        entries.add(FormData(name, email, selectedDate))
                        adapter.add("Nombre: $name, Teléfono: $email, Fecha: $selectedDate")

                        // Ocultar ProgressBar y habilitar botón
                        progressBar.visibility = View.GONE
                        submitBtn.isEnabled = true

                        nameEt.text.clear()
                        emailEt.text.clear()
                        Toast.makeText(this, "Entrada guardada", Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
