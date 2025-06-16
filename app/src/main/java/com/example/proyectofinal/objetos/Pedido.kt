package com.example.proyectofinal.objetos

data class Pedido(
    val domicilio: String,
    val fecha: String,
    val hora: String,
    val items: List<MenuItem> // Puedes usar tu clase ya existente
)