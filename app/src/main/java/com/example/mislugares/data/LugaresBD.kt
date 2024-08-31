package com.example.mislugares.datos

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.database.SQLException
import android.widget.Toast
import com.example.mislugares.Aplicacion
import com.example.mislugares.data.RepositorioLugares
import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar
import java.lang.Exception

open class LugaresBD(val contexto: Context) :
    SQLiteOpenHelper(contexto, "lugares", null, 1),
    RepositorioLugares {

    override fun onCreate(bd: SQLiteDatabase) {
        bd.execSQL(
            "CREATE TABLE lugares (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "direccion TEXT, " +
                    "longitud REAL, " +
                    "latitud REAL, " +
                    "tipo INTEGER, " +
                    "foto TEXT, " +
                    "telefono INTEGER, " +
                    "url TEXT, " +
                    "comentario TEXT, " +
                    "fecha BIGINT, " +
                    "valoracion REAL)"
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, " +
                    "'Facultad de Ingeniería Mecánica y Eléctrica', " +
                    "'Pedro de Alba SN, Niños Héroes, Ciudad Universitaria, 66455 San Nicolás de los Garza, N.L.', -100.3135, 25.7252, " +
                    TipoLugar.EDUCACION.ordinal + ", '', 962849300, " +
                    "'http://www.fime.uanl.mx/', " +
                    "'Vive la FIME.', " +
                    System.currentTimeMillis() + ", 3.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Alameda Monterrey', " +
                    "'Centro, 64000 Monterrey, N.L.', " +
                    " -100.32122196240864, 25.676197257990697, " + TipoLugar.NATURALEZA.ordinal + ", '', " +
                    "636472405, '', " + "'Parque central animado con senderos arbolados, jardines, loros, vendedores de comida y una glorieta..', " +
                    System.currentTimeMillis() + ", 4.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Macroplaza', " +
                    "'Centro, 64000 Monterrey, N.L.', -100.30934694394188,25.669368868283385," + TipoLugar.COMPRAS.ordinal + ", '', " +
                    "962849300, '', " +
                    "'Gran plaza con monumentos, fuentes y jardines, rodeada de museos y otros edificios." +
                    " .', " +
                    System.currentTimeMillis() + ", 5.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Cascadas del cerro de la silla', " +
                    "'Cerro de la Silla, Cascadas, del, Guadalupe, N.L.', " +
                    " -100.21034469213464,25.630261665919118, " + TipoLugar.NATURALEZA.ordinal + ", '', " +
                    "636472405, '', " + "'Naturaleza.', " +
                    System.currentTimeMillis() + ", 3.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Estadio Universitario', " +
                    "'Niños Héroes, Ciudad Universitaria, 66451 San Nicolás de los Garza, N.L.',-100.31074603958722, 25.72283366853118," +
                    TipoLugar.DEPORTE.ordinal + ", '', +528181586450, " +
                    "'', 'Estadio d3e los Tigres UANL', " +
                    System.currentTimeMillis() + ", 4.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Casa de Jairo', " +
                    "'C. Ramón de Campoamor 990', " +
                    " -100.31467, 25.731404, " + TipoLugar.BAR.ordinal + ", '', " +
                    "636472405, '', " + "'Casa de Jairo.', " +
                    System.currentTimeMillis() + ", 2.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Cerro de la Silla\n', " +
                    "'JQM8+24 Cerro de La Silla, Nuevo León', " +
                    " -100.23457212225664, 25.63397064306529, " + TipoLugar.NATURALEZA.ordinal + ", '', " +
                    "636472405, '', " + "'Esta montaña con forma de silla de montar tiene senderos empinados y vistas panorámicas desde la cima.', " +
                    System.currentTimeMillis() + ", 3.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Paseo Santa Lucía\n', " +
                    "'JQM8+24 Cerro de La Silla, Nuevo León', " +
                    " -100.28748203827637, 25.67569060881958, " + TipoLugar.ESPECTACULO.ordinal + ", '', " +
                    "636472405, '', " + "'Canal con senderos, murales al aire libre y viajes en bote al Museo de Historia Mexicana.', " +
                    System.currentTimeMillis() + ", 3.0)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Galerías Monterrey\n', " +
                    "'Av Insurgentes 2500, Vista Hermosa, 64620 Monterrey, N.L.', " +
                    " -100.35210218494791, 25.682096315735926, " + TipoLugar.COMPRAS.ordinal + ", '', " +
                    "8183484989, 'galerias.com', " + "'Centro comercial Galerías Mty', " +
                    System.currentTimeMillis() + ", 4.5)")
        )
        bd.execSQL(
            ("INSERT INTO lugares VALUES (null, 'Fashion Drive\n', " +
                    "'Av Insurgentes 2500, Vista Hermosa, 64620 Monterrey, N.L.', " +
                    " -100.33518135683066, 25.65333872784872, " + TipoLugar.COMPRAS.ordinal + ", '', " +
                    "8111613300, 'http://fashiondrive.mx/', " + "'Centro comercial de varios niveles con cadenas minoristas, boutiques modernas, restaurantes y un multicine.', " +
                    System.currentTimeMillis() + ", 4.0)")
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun elemento(id: Int): Lugar {
        val cursor = readableDatabase.rawQuery("SELECT * FROM lugares WHERE _id = $id", null)
        try {
            if (cursor.moveToNext())
                return extraeLugar(cursor)
            else
                throw SQLException("Error al acceder al elemento _id = $id")

        } catch (e:Exception) {
            throw e
        } finally {
            cursor?.close()
        }
    }

    override fun añade(lugar: Lugar) {
        TODO("Not yet implemented")
    }

    override fun nuevo(): Int {
        var _id = -1
        val lugar = Lugar(nombre = "")
        writableDatabase.execSQL("INSERT INTO lugares (nombre, direccion, " +
                "longitud, latitud, tipo, foto, telefono, url, comentario, fecha, " +
                "valoracion) VALUES ('', '', ${lugar.posicion.longitud}, " +
                "${lugar.posicion.latitud}, ${lugar.tipoLugar.ordinal}, '', 0, '', " +
                "'', ${lugar.fecha},0 )")
        val c = readableDatabase.rawQuery((
                "SELECT _id FROM lugares WHERE fecha = " + lugar.fecha), null)
        if (c.moveToNext()) _id = c.getInt(0)
        c.close()
        return _id
    }

    override fun borrar(id: Int) {
        val cursor = readableDatabase.rawQuery("SELECT * FROM lugares WHERE _id = $id", null)
        writableDatabase.execSQL("DELETE FROM lugares WHERE _id = $id")
    }

    override fun tamaño(): Int {
        TODO("Not yet implemented")
    }

    override fun actualizada(id: Int, lugar: Lugar) = with(lugar){
        writableDatabase.execSQL("UPDATE lugares SET " +
                "nombre = '$nombre', direccion = '$direccion', " +
                "longitud = ${posicion.longitud}, latitud = ${posicion.latitud}, "+
                "tipo = ${tipoLugar.ordinal}, foto = '$foto', telefono = $telefono, "+
                "url = '$url', comentario = '$comentarios', fecha = $fecha, "+
                "valoracion = $valoracion  WHERE _id = $id")
    }

    fun extraeLugar(cursor: Cursor) = Lugar(
        nombre = cursor.getString(1),
        direccion = cursor.getString(2),
        posicion = GeoPunto(
            cursor.getDouble(3),
            cursor.getDouble(4)
        ),
        tipoLugar = TipoLugar.entries[cursor.getInt(5)],
        foto = cursor.getString(6),
        telefono = cursor.getInt(7),
        url = cursor.getString(8),
        comentarios = cursor.getString(9),
        fecha = cursor.getLong(10),
        valoracion = cursor.getFloat(11)
    )

    fun extraeCursor(): Cursor {
        val pref = PreferenceManager.getDefaultSharedPreferences(contexto)
        var consulta = when (pref.getString("orden", "0")) {
            "0" -> "SELECT * FROM lugares "
            "1" -> "SELECT * FROM lugares ORDER BY valoracion DESC"
            else -> {
                val lon = (contexto.getApplicationContext() as Aplicacion).posicionActual.longitud
                val lat = (contexto.getApplicationContext() as Aplicacion).posicionActual.latitud
                "SELECT * FROM lugares ORDER BY " +
                        "(${lon} - longitud)*(${lon} - longitud) + " +
                        "(${lat} - latitud )*(${lat} - latitud )"
            }
        }
        consulta += " LIMIT ${pref.getString("maximo", "12")}"
        return readableDatabase.rawQuery(consulta, null)
    }


}