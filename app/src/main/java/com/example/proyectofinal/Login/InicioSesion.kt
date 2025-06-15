package com.example.proyectofinal.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.MainActivity
import com.example.proyectofinal.R
import com.example.proyectofinal.objetos.Usuario
import com.google.gson.Gson

class InicioSesion : AppCompatActivity() {

    //iniciar objetos del entorno visual
    private lateinit var usuarioEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var iniciarSesionButton: Button
    private lateinit var registrarseButton: Button
    private lateinit var mantenerSesionCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        verificarSesion()

        //asignar objetos del entorno visual
        usuarioEditText = findViewById(R.id.usuarioEditText)
        contrasenaEditText = findViewById(R.id.contrasenaEditText)
        iniciarSesionButton = findViewById(R.id.iniciarSesionButton)
        registrarseButton = findViewById(R.id.registrarseButton)
        mantenerSesionCheckBox = findViewById(R.id.mantenerSesionCheckBox)

        //asignar evento al boton
        iniciarSesionButton.setOnClickListener {iniciarSesion()}
        registrarseButton.setOnClickListener {registrarse()}
    }
    fun verificarSesion(){
        val sharedPref = getSharedPreferences("usuarios", MODE_PRIVATE)
        val mantenerSesion = sharedPref.getBoolean("mantenerSesion", false)
        if (mantenerSesion) {
            val usuario = sharedPref.getString("usuario", null)
            if (usuario != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun iniciarSesion() {
        val usuario = usuarioEditText.text.toString()
        val contrasena = contrasenaEditText.text.toString()

        //validar que los campos no esten vacios
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //validar que el usuario y la contraseña sean correctos
        val sharedPref = getSharedPreferences("usuarios", MODE_PRIVATE)
        val json = sharedPref.getString("usuarios", null)
        if (json != null) {
            val usuarios = Gson().fromJson(json, Array<Usuario>::class.java)
            for (usuarioJson in usuarios) {
                if (usuarioJson.usuario == usuario && usuarioJson.contrasena == contrasena) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    if (mantenerSesionCheckBox.isChecked) {
                        val editor = sharedPref.edit()
                        editor.putBoolean("mantenerSesion", true)
                        editor.putString("usuario", usuario)
                        editor.apply()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }
            }
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
        }

    }
    fun registrarse() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }
}