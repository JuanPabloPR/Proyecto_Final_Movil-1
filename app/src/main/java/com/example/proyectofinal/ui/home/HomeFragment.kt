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
            MenuItem(R.drawable.logo, "Platillo 1", "Descripción del platillo 1"),
            MenuItem(R.drawable.logo, "Platillo 2", "Descripción del platillo 2"),
            MenuItem(R.drawable.logo, "Platillo 3", "Descripción del platillo 3")
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