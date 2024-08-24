package com.example.mislugares.data

import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar

class LugaresLista : RepositorioLugares {
    val listaLugares = mutableListOf<Lugar>()

    init {
        añadeEjemplos()
    }

    override fun añadeEjemplos() {
        añade(
            Lugar(
                "Escuela Politécnica Superior de Gandia",
                "C/ Paranimf, 1 46730 Gandia (SPAIN)",
                GeoPunto(-0.166093, 38.995656),
                TipoLugar.EDUCACION,
                "foto3",
                0, "https://www.psg.upv.es",
                "uno de los mejores lugares para formarse",
                System.currentTimeMillis(), 3f)
        )

        añade(
            Lugar(
                "Al de siempre",
                "P. Industrial Junto Moli Nou - 466772",
                GeoPunto(-0.295058, 38.867180),
                TipoLugar.EDUCACION,
                "foto2",
                962849300, "https://androidcurso.com",
                "amplia tus conocimientos de android",
                System.currentTimeMillis(), 5f)
        )

        añade(
            Lugar(
                "Barranco del infierno",
                "Vía verde del rio serpis. Villalonga (Valencia)",
                GeoPunto(-0.1720092, 38.9705949),
                TipoLugar.NATURALEZA,
                "foto2",
                962849300, "https://sosegaos.blogspot.com.es",
                "arroz de calabaza",
                System.currentTimeMillis(), 4f)
        )

        añade(
            Lugar(
                "La Vital",
                "Avda. de La Vital, 0 46701 Gandía (Valencia)",
                GeoPunto(-0.190642, 38.925857),
                TipoLugar.COMPRAS,
                "foto5",
                962849300, "https://sosegaos.blogspot.com.es",
                "el tipico centro comercial",
                System.currentTimeMillis(), 4f)
        )
    }

    override fun elemento(id: Int): Lugar {
        return listaLugares[id]
    }

    override fun añade(lugar: Lugar) {
        listaLugares.add(lugar)
    }

    override fun nuevo(): Int {
        val lugar = Lugar("Escuela Politécnica Superior de Gandia",
            "C/ Paranimf, 1 46730 Gandia (SPAIN)",
            GeoPunto(-0.166093, 38.995656),
            TipoLugar.EDUCACION,
            "foto3",
            962849300, "https://www.psg.upv.es",
            "uno de los mejores lugares para formarse",
            System.currentTimeMillis(), 3f)
        listaLugares.add(lugar)
        return listaLugares.size - 1

    }

    override fun borrar(id: Int) {
        listaLugares.removeAt(id)
    }

    override fun tamaño(): Int {
        return listaLugares.size
    }

    override fun actualizada(id: Int, lugar: Lugar) {
        listaLugares[id] = lugar
    }
}