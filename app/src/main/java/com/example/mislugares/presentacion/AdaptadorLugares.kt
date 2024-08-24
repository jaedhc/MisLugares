package com.example.mislugares.presentacion

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mislugares.Aplicacion
import com.example.mislugares.R
import com.example.mislugares.data.RepositorioLugares
import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar

open class AdaptadorLugares(val lugares: RepositorioLugares):
    RecyclerView.Adapter<AdaptadorLugares.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun personaliza(lugar:Lugar) = with(itemView){
            val nombre:TextView = itemView.findViewById(R.id.nombre)
            val direccion:TextView = itemView.findViewById(R.id.direccion)
            val foto:ImageView = itemView.findViewById(R.id.foto)
            val valoracion:RatingBar = itemView.findViewById(R.id.valoracion)
            val distancia:TextView = itemView.findViewById(R.id.distancia)

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

            val posicion = (context.applicationContext as Aplicacion).posicionActual
            if (posicion== GeoPunto.SIN_POSICION || lugar.posicion== GeoPunto.SIN_POSICION) {
                distancia.text = "... Km"
            } else {
                val d = posicion.distancia(lugar.posicion).toInt()
                distancia.text = if (d < 2000) "$d m"
                else          "${(d / 1000)} Km"

            }
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
            val i = Intent(holder.itemView.context, VistaLugarActivity::class.java)
            i.putExtra("pos", position)
            holder.itemView.context.startActivity(i)
        }
    }

}