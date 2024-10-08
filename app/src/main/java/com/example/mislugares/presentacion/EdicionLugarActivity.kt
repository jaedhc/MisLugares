package com.example.mislugares.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.mislugares.Aplicacion
import com.example.mislugares.R
import com.example.mislugares.casos_uso.CasosUsoLugar
import com.example.mislugares.dominio.Lugar
import com.example.mislugares.dominio.TipoLugar

class EdicionLugarActivity : AppCompatActivity() {

    private val lugares by lazy { (application as Aplicacion).lugares }
    val adaptador by lazy { (application as Aplicacion).adaptador }
    private val usoLugar by lazy { CasosUsoLugar(this, lugares, adaptador) }
    private var pos = 1
    private lateinit var lugar: Lugar
    var _id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edicion_lugar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        pos = intent.extras?.getInt("pos", -1) ?: 0
        _id = intent.extras?.getInt("_id", -1) ?: 0
        lugar = if (_id != -1) lugares.elemento(_id)
        else           adaptador.lugarPosicion(pos)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        actualizaVistas()

    }

    fun actualizaVistas(){
        val adaptador = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item,
            lugar.tipoLugar.getNombres())

        val tipo = findViewById<Spinner>(R.id.spinner)

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipo.adapter = adaptador
        tipo.setSelection(lugar.tipoLugar.ordinal)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edicion_lugar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = adaptador.posicionId(pos)
        when (item.itemId) {
            R.id.accion_guardar -> {
                val nombre = findViewById<EditText>(R.id.txtNombre)
                val tipo = findViewById<Spinner>(R.id.spinner)
                val direction = findViewById<EditText>(R.id.txtDireccion)
                val telefono = findViewById<EditText>(R.id.txtTelefono)
                val url = findViewById<EditText>(R.id.txtPagina)
                val comentarios = findViewById<EditText>(R.id.txtComentario)

                val nuevoLugar = Lugar(nombre.text.toString(),
                    direction.text.toString(),
                    lugar.posicion,
                    TipoLugar.values()[tipo.selectedItemPosition],
                    lugar.foto,
                    telefono.text.toString().toInt(),
                    url.text.toString(),
                    comentarios.text.toString(),
                    lugar.fecha, lugar.valoracion)
                if (_id==-1) _id = adaptador.idPosicion(pos)
                usoLugar.guardar(_id, nuevoLugar)
                finish()
                return true
            }
            R.id.accion_cancelar -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}