package com.example.proyectofinal

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.objetos.MenuAdaptador
import com.example.proyectofinal.objetos.MenuItem
import com.example.proyectofinal.objetos.Pedido
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnviarProductos : AppCompatActivity() {

    //iniciar objetos del entorno visual
    private lateinit var domicilioEditText: EditText
    private lateinit var enviarButton: Button
    private lateinit var enviarProductosRecyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_enviar_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //asignar objetos del entorno visual
        domicilioEditText = findViewById(R.id.domicilioEditText)
        enviarButton = findViewById(R.id.enviarButton)
        enviarProductosRecyclerView = findViewById(R.id.enviarProductosRecyclerView)

        //obtener productos del carrito
        val productos = obtenerProductos()
        enviarProductosRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdaptador(productos)
        enviarProductosRecyclerView.adapter = adapter


        //asignar evento al boton
        enviarButton.setOnClickListener {enviarProductos()}
    }

    private fun obtenerProductos(): List<MenuItem> {
        val sharedPreferences = getSharedPreferences("carrito", MODE_PRIVATE)
        val carrito = sharedPreferences.getString("carrito", null)
        return if (carrito != null) {
            Gson().fromJson(carrito, Array<MenuItem>::class.java).toList()
        } else {
            Toast.makeText(this, "No hay productos en el carrito", Toast.LENGTH_SHORT).show()
            finish()
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun enviarProductos() {
        val sharedPreferences = getSharedPreferences("carrito", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val sharedPreferences2 = getSharedPreferences("pedidos", MODE_PRIVATE)
        val pedido = sharedPreferences2.getString("pedido", null)
        if (pedido != null) {
            val pedidoJson = Gson().fromJson(pedido, Array<Pedido>::class.java).toMutableList()
            val fecha = LocalDateTime.now()
            val nuevoPedido = Pedido(domicilioEditText.text.toString(), fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fecha.format(DateTimeFormatter.ofPattern("HH:mm")), obtenerProductos())
            pedidoJson.add(nuevoPedido)
            sharedPreferences2.edit().putString("pedido", Gson().toJson(pedidoJson)).apply()
        }else{
            val fecha = LocalDateTime.now()
            val nuevoPedido = Pedido(domicilioEditText.text.toString(), fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fecha.format(DateTimeFormatter.ofPattern("HH:mm")), obtenerProductos())
            sharedPreferences2.edit().putString("pedido", Gson().toJson(listOf(nuevoPedido))).apply()
        }
        editor.remove("carrito")
        editor.apply()
        Toast.makeText(this, "Productos enviados", Toast.LENGTH_SHORT).show()
        finish()
    }
}