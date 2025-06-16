package com.example.proyectofinal.objetos

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class PedidoAdapter(
    private val pedidos: List<Pedido>
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    inner class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDomicilio: TextView = view.findViewById(R.id.tvDomicilio)
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.tvDomicilio.text = pedido.domicilio
        holder.tvFechaHora.text = "${pedido.fecha} ${pedido.hora}"
        Toast.makeText(holder.itemView.context, pedido.domicilio, Toast.LENGTH_SHORT).show()

        holder.itemView.setOnClickListener {
            val items = pedido.items
            var productos = ""
            for (item in items) {
                productos += "${item.title} \n"
            }
            //mostrar todos los items en un alertDialog
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Items del pedido")
            builder.setMessage(productos)
            builder.show()
        }
    }

    override fun getItemCount(): Int = pedidos.size
}
