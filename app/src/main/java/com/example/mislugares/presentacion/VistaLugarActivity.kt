package com.example.mislugares.presentacion

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.mislugares.Aplicacion
import com.example.mislugares.R
import com.example.mislugares.casos_uso.CasoUsoEditar
import com.example.mislugares.casos_uso.CasosUsoLugar
import com.example.mislugares.dominio.Lugar
import java.text.DateFormat
import java.util.Date

class VistaLugarActivity : AppCompatActivity() {

    // Variables
    private val lugares by lazy { (application as Aplicacion).lugares }
    val adaptador by lazy { (application as Aplicacion).adaptador }
    private val usoLugar by lazy { CasosUsoLugar(this, lugares, adaptador) }
    private val usoEditar by lazy { CasoUsoEditar(this, lugares) }
    private var pos = 1
    val RESULTADO_EDITAR = 1234
    private lateinit var lugar: Lugar
    val RESULTADO_GALERIA = 2
    val RESULTADO_FOTO = 3
    private var uriUltimaFoto: Uri? = null

    lateinit var foto : ImageView

    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_lugar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Obtiene la posición del lugar
        pos = intent.extras?.getInt("pos", 0) ?: 0


        val btnDire = this.findViewById<LinearLayout>(R.id.ll_direccion)
        val btnTel = this.findViewById<LinearLayout>(R.id.ll_telefono)
        val btnUrl = this.findViewById<LinearLayout>(R.id.ll_url)

        // Obtiene el lugar
        lugar = adaptador.lugarPosicion(pos)

        btnDire.setOnClickListener{
            verMapa()
        }

        btnTel.setOnClickListener{
            llamarTelefono()
        }

        btnUrl.setOnClickListener{
            verPgWeb()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val galeria = this.findViewById<ImageView>(R.id.galeria)
        val camara = this.findViewById<ImageView>(R.id.camara)
        foto = this.findViewById(R.id.foto)
        val eliminar = this.findViewById<ImageView>(R.id.eliminar)
        galeria.setOnClickListener{
            ponerDeGaleria()
        }

        camara.setOnClickListener{
            tomarFoto()
        }

        eliminar.setOnClickListener{
            eliminarFoto()
        }

        // Actualiza las vistas
        actualizaVistas()
    }


    // Función para actualizar las vistas
    fun tomarFoto() {
        uriUltimaFoto = usoLugar.toamrFoto(RESULTADO_FOTO)
    }

    fun eliminarFoto(){
        val alert = AlertDialog.Builder(this)

        alert.setTitle("Alerta, a punto de borrar la imagen")
        alert.setMessage("¿Desea borrar la imagen?")
        alert.setPositiveButton("Confirmar") {
            dialog, id ->
            usoLugar.ponerFoto(pos, "", foto)
        }
        alert.setNegativeButton("Cancelar"){
            dialog, id ->
            dialog.dismiss()
        }

        alert.show()

    }

    fun ponerDeGaleria() = usoLugar.ponerDeGaleria(RESULTADO_GALERIA)

    fun verMapa() = usoLugar.verMapa(lugar)
    fun llamarTelefono() = usoLugar.llamarTelefono(lugar)
    fun verPgWeb() = usoLugar.verPgWeb(lugar)

    private fun actualizaVistas() {
        val nombre = findViewById<TextView>(R.id.nombre)
        val logo_tipo = findViewById<ImageView>(R.id.logo_tipo)
        val tipo = findViewById<TextView>(R.id.tipo)
        val direccion = findViewById<TextView>(R.id.direccion)
        val telefono = findViewById<TextView>(R.id.telefono)
        val url = findViewById<TextView>(R.id.url)
        val comentario = findViewById<TextView>(R.id.comentario)
        val fecha = findViewById<TextView>(R.id.fecha)
        val hora = findViewById<TextView>(R.id.hora)
        val valoracion = findViewById<RatingBar>(R.id.valoracion)

        nombre.text = lugar.nombre
        logo_tipo.setImageResource(lugar.tipoLugar.recurso)
        tipo.text = lugar.tipoLugar.texto
        direccion.text = lugar.direccion

        telefono.text = Integer.toString(lugar.telefono)

        url.text = lugar.url
        comentario.text = lugar.comentarios
        fecha.text = DateFormat.getDateInstance().format(Date(lugar.fecha))
        hora.text = DateFormat.getTimeInstance().format(Date(lugar.fecha))

        valoracion.rating = lugar.valoracion
        valoracion.setOnRatingBarChangeListener{
            ratingBar, valor, fromUser -> lugar.valoracion = valor
        }
        valoracion.setOnRatingBarChangeListener{ _, valor, _ ->
            val _id = adaptador.idPosicion(pos)
            lugar.valoracion = valor
            lugares.actualizada(id = _id, lugar = lugar)
            adaptador.cursor = lugares.extraeCursor()
            adaptador.notifyDataSetChanged()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.vista_lugar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.accion_compartir -> {
                usoLugar.compartir(lugar)
                return true
            }
            R.id.accion_llegar -> {
                verMapa()
                return true
            }
            R.id.accion_editar -> {
                usoLugar.editar(pos, RESULTADO_EDITAR)
                return true
            }
            R.id.accion_borrar -> {

                AlertDialog.Builder(this)
                    .setTitle("Titulo")
                    .setMessage("Desea borrar el lugar?")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("Confirmar"){
                        dialog, id ->
                        usoLugar.borrar(pos)
                    }.setNegativeButton("Cancelar"){
                        dialog, id ->
                        dialog.dismiss()
                    }
                    .show()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == RESULTADO_EDITAR){
            actualizaVistas()
        } else if( requestCode == RESULTADO_GALERIA) {
            if(requestCode == RESULT_OK){
                usoLugar.ponerFoto(pos, data?.dataString ?: "", foto)
            } else {
                Toast.makeText(this, "Foto no cargada", Toast.LENGTH_LONG).show()
            }
        } else if(requestCode == RESULTADO_FOTO){
            if(requestCode == Activity.RESULT_OK && uriUltimaFoto!=null){
                lugar.foto = uriUltimaFoto.toString()
                usoLugar.ponerFoto(pos, lugar.foto, foto)
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show()
            }
        }
    }
}