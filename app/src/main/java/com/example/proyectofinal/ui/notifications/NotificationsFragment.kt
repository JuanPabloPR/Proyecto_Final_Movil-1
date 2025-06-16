package com.example.proyectofinal.ui.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.example.proyectofinal.Login.InicioSesion
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //iniciar objetos del entorno visual
    private lateinit var cerrarSesionButton: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var opinionEditText: EditText
    private lateinit var enviarButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //asignar objetos del entorno visual
        cerrarSesionButton = binding.cerrarSesionButton
        ratingBar = binding.ratingBar
        opinionEditText = binding.opinionEditText
        enviarButton = binding.enviarButton

        cerrarSesionButton.setOnClickListener {cerrarSesion()}
        enviarButton.setOnClickListener {enviarOpinion()}

        return root
    }

    private fun enviarOpinion() {
        var rating = ratingBar.rating
        var opinion = opinionEditText.text.toString()

        ratingBar.rating = 0f
        opinionEditText.text.clear()

        Toast.makeText(requireContext(), "Gracias por tu opinion", Toast.LENGTH_SHORT).show()
    }

    private fun cerrarSesion() {

        var sharedPreferences = requireContext().getSharedPreferences("usuarios", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putBoolean("mantenerSesion", false)
        editor.remove("usuario")
        editor.apply()
        var intent = Intent(requireContext(), InicioSesion::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}