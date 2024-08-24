package com.example.mislugares.presentacion

import android.content.Intent
import android.database.Cursor
import android.view.View
import android.widget.Toast
import com.example.mislugares.datos.LugaresBD
import com.example.mislugares.dominio.Lugar


class AdaptadorLugaresBD(lugares: LugaresBD, var cursor: Cursor) : AdaptadorLugares(lugares) {

    fun lugarPosicion(posicion: Int): Lugar {
        cursor.moveToPosition(posicion)
        return (lugares as LugaresBD).extraeLugar(cursor)
    }

    fun idPosicion(posicion: Int): Int {
        cursor.moveToPosition(posicion)
        if (cursor.count>0)  return cursor.getInt(0)
        else return -1
    }

    fun posicionId(id: Int): Int {
        var pos = 0
        while (pos < itemCount && idPosicion(pos) != id) pos++
        return if (pos >= itemCount) -1
        else                  pos
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lugar = lugarPosicion(position)
        holder.personaliza(lugar)
        holder.view.tag = position

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "$position", Toast.LENGTH_LONG).show()
            val i = Intent(holder.itemView.context, VistaLugarActivity::class.java)
            i.putExtra("pos", position)
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return cursor.getCount()
    }

}