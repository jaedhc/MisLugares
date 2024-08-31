package com.example.mislugares.casos_uso

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.mislugares.Aplicacion
import com.example.mislugares.data.RepositorioLugares
import com.example.mislugares.datos.LugaresBD
import com.example.mislugares.dominio.GeoPunto
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.presentacion.AdaptadorLugaresBD
import com.example.mislugares.presentacion.EdicionLugarActivity
import com.example.mislugares.presentacion.VistaLugarActivity
import java.io.File
import java.io.IOException

class CasosUsoLugar (val actividad: Activity,
    val lugares: LugaresBD, open val adaptador: AdaptadorLugaresBD
){

    fun mostrar(pos:Int){
        val i = Intent(actividad, VistaLugarActivity::class.java)
        i.putExtra("pos", pos)
        actividad.startActivity(i)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun guardar(id: Int, nuevoLugar: Lugar){
        val i = Intent(actividad, VistaLugarActivity::class.java)
        lugares.actualizada(id, nuevoLugar)
        adaptador.cursor = lugares.extraeCursor()
        adaptador.notifyDataSetChanged()
        actividad.finish()
    }

    fun nuevo() {
        val _id = lugares.nuevo()
        //ponemos la posición actual en el nuevo lugar
        val posicion = (actividad.application as Aplicacion).posicionActual
        if (posicion != GeoPunto.SIN_POSICION) {
            val lugar = lugares.elemento(_id)
            lugar.posicion = posicion
            lugares.actualizada(_id, lugar)
        }
        val i = Intent(actividad, EdicionLugarActivity::class.java)
        i.putExtra("_id", _id)
        actividad.startActivity(i)
    }

    fun borrar(pos:Int){
        lugares.borrar(pos)
        adaptador.cursor = lugares.extraeCursor()
        adaptador.notifyDataSetChanged()
        actividad.finish()
    }

    fun editar(pos: Int, codidoSolicitud:Int){
        val i = Intent(actividad, EdicionLugarActivity::class.java)
        i.putExtra("pos", pos)
        actividad.startActivityForResult(i, codidoSolicitud)
        actividad.finish()
    }

    fun compartir(lugar: Lugar) = actividad.startActivity(
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$(Lugar.nombre) - $(lugar.url)")
        }
    )

    fun llamarTelefono(lugar: Lugar) = actividad.startActivity(
        Intent(Intent.ACTION_DIAL, Uri.parse(lugar.url))
    )

    fun verPgWeb(lugar: Lugar) = actividad.startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(lugar.url))
    )

    fun verMapa(lugar: Lugar){
        val lat = lugar.posicion.latitud
        val lon = lugar.posicion.longitud
        val uri = if (lugar.posicion != GeoPunto.SIN_POSICION){
            Uri.parse("geo:$lat,$lon")
        } else {
            Uri.parse("geo:0,0?q=" + lugar.direccion)
        }
        actividad.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun ponerDeGaleria(codigoSolicitud: Int){

    }

    fun ponerFoto(pos: Int, uri:String?, imageView: ImageView){
        val lugar = adaptador.lugarPosicion(pos)
        lugar.foto = uri ?: ""
        visualizarFoto(lugar, uri, imageView)
        actualizaPosLugar(pos, lugar)
    }

    fun actualizaPosLugar(pos: Int, lugar: Lugar) {
        val id = adaptador.idPosicion(pos)
        guardar(id, lugar)
        //lugares.actualiza(id, lugar);
        //adaptador.cursor = lugares.extraeCursor()
        //adaptador.notifyDataSetChanged()
    }

    fun visualizarFoto(lugar: Lugar, uri:String?, imageView: ImageView){
        if(!(lugar.foto == null || lugar.foto.isEmpty())){
            imageView.setImageURI(Uri.parse(uri))
        } else {
            imageView.setImageBitmap(null)
        }
    }

    fun toamrFoto(codidoSolicitud: Int): Uri?{
        try {
            val file = File.createTempFile(
                "img_" + System.currentTimeMillis() / 1000, ".jpg",
                actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            val uriUltimaFoto = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    actividad, "es.upv.jtomas.mislugares.fileProvider", file
                )
            } else {
                Uri.fromFile(file)
            }
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriUltimaFoto)
            actividad.startActivityForResult(intent, codidoSolicitud)
            return uriUltimaFoto
        } catch (ex: IOException){
            Toast.makeText(actividad, "Error al crear fichero", Toast.LENGTH_LONG).show()
            return null
        }
    }

}