package com.example.proyectofinal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.FragmentHomeBinding
import com.example.proyectofinal.objetos.MenuAdaptador
import com.example.proyectofinal.objetos.MenuItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //iniciar objetos del entorno visual
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //asignar objetos del entorno visual
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val items = listOf(
            MenuItem(R.drawable.tacosbirria, "Tacos de Birria", "Tacos suaves rellenos de birria de res marinada en chiles y especias, acompañados de consomé para sumergir.\n\nPrecio: \$75", true),
            MenuItem(R.drawable.enchiladas, "Enchiladas Verdes de Pollo", "Tortillas rellenas de pollo deshebrado bañadas en salsa verde de tomatillo, crema y queso fresco.\n\nPrecio: \$90", true),
            MenuItem(R.drawable.sopes, "Sopes de Chorizo con Papa", "Sopes gruesos de maíz cubiertos con frijoles, chorizo, papa, lechuga, crema y queso.\n\nPrecio: \$60", true),
            MenuItem(R.drawable.tamal, "Tamal de Elote Dulce", "Tamal suave hecho con maíz tierno y un toque de azúcar, ideal para acompañar con café.\n\nPrecio: \$25", true),
            MenuItem(R.drawable.aguas, "Aguas Frescas", "Bebidas tradicionales refrescantes elaboradas artesanalmente.\n\nPrecio: \$20", true),
            MenuItem(R.drawable.pozole, "Pozole Rojo", "Descripción: Guiso tradicional de maíz y carne de cerdo en caldo rojo, servido con lechuga, rábanos, cebolla y tostadas.\n\nPrecio: \$95", true)
        )

        val adapter = MenuAdaptador(items)
        recyclerView.adapter = adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}