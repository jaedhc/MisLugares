package com.example.mislugares.presentacion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mislugares.Aplicacion
import com.example.mislugares.R
import com.example.mislugares.casos_uso.CasosUsoActividades
import com.example.mislugares.casos_uso.CasosUsoLocalizacion
import com.example.mislugares.casos_uso.CasosUsoLugar
import com.example.mislugares.databinding.ActivityMain2Binding
import com.google.android.material.floatingactionbutton.FloatingActionButton

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class MainActivity2 : AppCompatActivity() {

    val SOLICITUD_PERMISO_LOCALIZACION = 1
    val RESULTADO_PREFERENCIAS = 0

    private lateinit var binding: ActivityMain2Binding

    val lugares by lazy {(application as Aplicacion).lugares}
    val adaptador by lazy { (application as Aplicacion).adaptador }
    val usoLugar by lazy { CasosUsoLugar(this, lugares, adaptador) }
    val usoLocalizacion by lazy { CasosUsoLocalizacion(this, SOLICITUD_PERMISO_LOCALIZACION)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //lugares.añadeEjemplos()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                SOLICITUD_PERMISO_LOCALIZACION)
        } else {
            // El permiso ya fue concedido, procede con la funcionalidad
            usoLocalizacion.permisoConcedido()
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adaptador
        }

        val btn = findViewById<Button>(R.id.btn_mapa)
        val add = findViewById<FloatingActionButton>(R.id.btn_add)

        btn.setOnClickListener{
            val i = Intent(this, MapaActivity::class.java)
            startActivity(i)
        }

        add.setOnClickListener{
            usoLugar.nuevo()
        }

        binding.btnSettings.setOnClickListener{
            lanzarPreferencias(binding.root)
        }


    }

    fun lanzarPreferencias(view: View? = null) = startActivityForResult(
        Intent(this, PreferenciasActivity::class.java), RESULTADO_PREFERENCIAS
    )

    override
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULTADO_PREFERENCIAS) {
            adaptador.cursor = lugares.extraeCursor()
            adaptador.notifyDataSetChanged()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                usoLocalizacion.permisoConcedido()
                /*ultimaLocalizazion()
                activarProveedores()
                usoLugar.adaptador.notifyDataSetChanged()*/
            }
        }
    }
}
