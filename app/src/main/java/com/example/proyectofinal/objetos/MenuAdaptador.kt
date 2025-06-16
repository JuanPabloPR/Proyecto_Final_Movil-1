package com.example.proyectofinal.objetos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

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
            val item = itemList[position]
            holder.imgFood.setImageResource(item.imageResId)
            holder.txtTitle.text = item.title
            holder.txtDescription.text = item.description

            holder.btnOrder.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Ordenaste: ${item.title}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getItemCount(): Int = itemList.size
}