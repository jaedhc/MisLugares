package com.example.mislugares.data

import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar

interface RepositorioLugares {
    fun elemento(id: Int): Lugar
    fun añade(lugar: Lugar)
    fun nuevo(): Int
    fun borrar(id: Int)
    fun tamaño(): Int
    fun actualizada(id: Int, lugar: Lugar)

    fun añadeEjemplos(){

    }

}