package com.example.mislugares.dominio

data class Lugar (val nombre:String = "",
                  var direccion: String = "",
                  var posicion: GeoPunto = GeoPunto.SIN_POSICION,
                  var tipoLugar: TipoLugar = TipoLugar.OTROS,
                  var foto: String = "",
                  var telefono:Int = 0,
                  var url: String = "",
                  var comentarios: String = "",
                  var fecha: Long = System.currentTimeMillis(),
                  var valoracion: Float = 3.5F
    ) {

}