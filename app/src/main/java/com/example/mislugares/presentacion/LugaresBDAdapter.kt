package com.example.mislugares.presentacion

import android.content.Context
import com.example.mislugares.Aplicacion
import com.example.mislugares.datos.LugaresBD

class LugaresBDAdapter(contexto: Context, val adaptador: AdaptadorLugaresBD): LugaresBD(contexto) {

    fun elementoPos(pos:Int) = adaptador.lugarPosicion(pos)
}