package com.example.proyectofinal.Login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.R
import com.example.proyectofinal.objetos.Usuario
import com.google.gson.Gson

class Registro : AppCompatActivity() {
    //iniciar objetos del entorno visual
    private lateinit var nombreEditText: EditText
    private lateinit var numeroTelefonoEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var confirmarContrasenaEditText: EditText
    private lateinit var registrarseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //asignar objetos del entorno visual
        nombreEditText = findViewById(R.id.nombreEditText)
        numeroTelefonoEditText = findViewById(R.id.numeroTelefonoEditText)
        usuarioEditText = findViewById(R.id.usuarioEditText)
        contrasenaEditText = findViewById(R.id.contrasenaEditText)
        confirmarContrasenaEditText = findViewById(R.id.confirmarContrasenaEditText)
        registrarseButton = findViewById(R.id.registrarseButton)

        //asignar evento al boton
        registrarseButton.setOnClickListener {registrarse()}
    }

    fun registrarse() {
        val nombre = nombreEditText.text.toString()
        val numeroTelefono = numeroTelefonoEditText.text.toString()
        val usuario = usuarioEditText.text.toString()
        val contrasena = contrasenaEditText.text.toString()
        val confirmarContrasena = confirmarContrasenaEditText.text.toString()

        //validar que los campos no esten vacios
        if (nombre.isEmpty() || numeroTelefono.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //validar que las contraseñas sean iguales
        if (contrasena != confirmarContrasena) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        //guardar los datos del usuario en shared preferences en json
        val sharedPref = getSharedPreferences("usuarios", MODE_PRIVATE)
        val json = sharedPref.getString("usuarios", null)
        val editor = sharedPref.edit()

        if (json != null) {
            val usuariosJson = Gson().fromJson(json, Array<Usuario>::class.java).toMutableList()
            for (usuarioJson in usuariosJson) {
                if (usuarioJson.usuario == usuario) {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            usuariosJson.add(Usuario(nombre, numeroTelefono, usuario, contrasena))
            editor.putString("usuarios", Gson().toJson(usuariosJson))
            editor.apply()
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            editor.putString("usuarios", Gson().toJson(mutableListOf(Usuario(nombre, numeroTelefono, usuario, contrasena))))
            editor.apply()
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}