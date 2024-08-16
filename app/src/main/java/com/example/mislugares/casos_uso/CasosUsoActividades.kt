package com.example.mislugares.casos_uso

import android.app.Activity
import android.content.Intent
import com.example.mislugares.presentacion.AcercaDeActivity
import com.example.mislugares.presentacion.PreferenciasActivity

class CasosUsoActividades (val actividad: Activity) {

    fun lanzarAcercaDe(){
        val i = Intent(actividad, AcercaDeActivity::class.java)
        actividad.startActivity(i)
    }

    fun lanzarPreferencias(){
        val i = Intent(actividad, PreferenciasActivity::class.java)
        actividad.startActivity(i)
    }
}