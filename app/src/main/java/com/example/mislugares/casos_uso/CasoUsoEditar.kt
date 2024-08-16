package com.example.mislugares.casos_uso

import android.app.Activity
import android.content.Intent
import com.example.mislugares.data.RepositorioLugares
import com.example.mislugares.presentacion.EdicionLugarActivity
import com.example.mislugares.presentacion.VistaLugarActivity

class CasoUsoEditar (val actividad: Activity,
                     val lugares: RepositorioLugares){

    fun mostrarEdicion(pos:Int){
        val i = Intent(actividad, EdicionLugarActivity::class.java)
        i.putExtra("pos", pos)
        actividad.startActivity(i)
    }

}