package com.example.proyectofinal.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.PromocionesActivity
import com.example.proyectofinal.EnviarProductos
import com.example.proyectofinal.Historial
import com.example.proyectofinal.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //iniciar objetos del entorno visual
    private lateinit var botonEntrega : Button
    private lateinit var botonHistorial : Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //asignar objetos del entorno visual
        botonEntrega = binding.btnEntregas
        botonHistorial = binding.btnHistorial

        //asignar evento al boton
        botonEntrega.setOnClickListener {
            val intent = Intent(context, EnviarProductos::class.java)
            startActivity(intent)
        }
        botonHistorial.setOnClickListener {
            val intent = Intent(context, Historial::class.java)
            startActivity(intent)
        }



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPromociones.setOnClickListener {
            val intent = Intent(requireContext(), PromocionesActivity::class.java)
            startActivity(intent)
        }
    }

}