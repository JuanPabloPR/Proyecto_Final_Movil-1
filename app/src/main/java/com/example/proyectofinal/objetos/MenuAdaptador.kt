package com.example.proyectofinal.objetos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.google.gson.Gson


class MenuAdaptador(private val itemList: List<MenuItem>) : RecyclerView.Adapter<MenuAdaptador.MenuViewHolder>() {

        inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgFood: ImageView = itemView.findViewById(R.id.imgFood)
            val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
            val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
            val btnOrder: Button = itemView.findViewById(R.id.btnOrder)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
            return MenuViewHolder(view)
        }

        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
            var item = itemList[position]
            holder.imgFood.setImageResource(item.imageResId)
            holder.txtTitle.text = item.title
            holder.txtDescription.text = item.description
            holder.btnOrder.visibility = if (item.isVisible) View.VISIBLE else View.GONE

            holder.btnOrder.setOnClickListener {
                val sharedPreferences = holder.itemView.context.getSharedPreferences("carrito", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val carrito = sharedPreferences.getString("carrito", null)

                if(carrito != null){
                    val carritoJson = Gson().fromJson(carrito, Array<MenuItem>::class.java).toMutableList()
                    item.isVisible = false
                    carritoJson.add(item)
                    editor.putString("carrito", Gson().toJson(carritoJson))
                    editor.apply()
                    Toast.makeText(holder.itemView.context, "Producto agregado para envio", Toast.LENGTH_SHORT).show()
                }else{
                    item.isVisible = false
                    editor.putString("carrito", Gson().toJson(listOf(item)))
                    editor.apply()
                    Toast.makeText(holder.itemView.context, "Producto agregado para envio", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun getItemCount(): Int = itemList.size
}