package com.example.mislugares


import android.app.Application
import com.example.mislugares.data.AdaptadorLugares
import com.example.mislugares.data.LugaresLista

class Aplicacion: Application() {
    val lugares = LugaresLista()
    val adaptador = AdaptadorLugares(lugares)
}
