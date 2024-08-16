package com.example.mislugares.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mislugares.R
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar

class AdaptadorLugares(val lugares: RepositorioLugares):
    RecyclerView.Adapter<AdaptadorLugares.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun personaliza(lugar:Lugar) = with(itemView){
            val nombre:TextView = itemView.findViewById(R.id.nombre)
            val direccion:TextView = itemView.findViewById(R.id.direccion)
            val foto:ImageView = itemView.findViewById(R.id.foto)
            val valoracion:RatingBar = itemView.findViewById(R.id.valoracion)

            nombre.text = lugar.nombre
            direccion.text = lugar.direccion


            foto.setImageResource(
                when(lugar.tipoLugar){
                    TipoLugar.RESTAURANTE -> R.drawable.restaurante
                    TipoLugar.BAR -> R.drawable.bar
                    TipoLugar.COPAS -> R.drawable.copas
                    TipoLugar.ESPECTACULO -> R.drawable.espectaculos
                    TipoLugar.HOTEL -> R.drawable.hotel
                    TipoLugar.COMPRAS -> R.drawable.compras
                    TipoLugar.EDUCACION -> R.drawable.educacion
                    TipoLugar.DEPORTE -> R.drawable.deporte
                    TipoLugar.NATURALEZA -> R.drawable.naturaleza
                    TipoLugar.GASOLINERA -> R.drawable.gasolinera
                    TipoLugar.OTROS -> R.drawable.otros
                }
            )
            foto.scaleType = ImageView.ScaleType.FIT_END
            valoracion.rating= lugar.valoracion

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.elemento_lista, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int = lugares.tamaño()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lugar = lugares.elemento(position)
        holder.personaliza(lugar)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "$position", Toast.LENGTH_LONG).show()
        }
    }

}