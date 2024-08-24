package com.example.mislugares


import android.app.Application
import com.example.mislugares.datos.LugaresBD
import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.presentacion.AdaptadorLugaresBD
import com.example.mislugares.presentacion.LugaresBDAdapter

class Aplicacion: Application() {
    val posicionActual = GeoPunto(0.0, 0.0)
    val lugares = LugaresBD(this)
    val adaptador by lazy { AdaptadorLugaresBD(lugares, lugares.extraeCursor()) }


}
