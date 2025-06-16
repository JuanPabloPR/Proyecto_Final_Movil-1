package com.example.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.objetos.Pedido
import com.example.proyectofinal.objetos.PedidoAdapter
import com.google.gson.Gson

class Historial : AppCompatActivity() {
    //iniciar objetos del entorno visual
    private lateinit var enviarProductosRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        enviarProductosRecyclerView = findViewById(R.id.enviarProductosRecyclerView)

        val historial = obtenerHistorial()
        enviarProductosRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PedidoAdapter(historial)
        enviarProductosRecyclerView.adapter = adapter
    }

    private fun obtenerHistorial(): List<Pedido> {
        val sharedPreferences = getSharedPreferences("pedidos", MODE_PRIVATE)
        val historial = sharedPreferences.getString("pedido", null)
        return if (historial != null) {
            Gson().fromJson(historial, Array<Pedido>::class.java).toList()
        } else {
            Toast.makeText(this, "No hay productos en el carrito", Toast.LENGTH_SHORT).show()
            finish()
            emptyList()
        }
    }
}