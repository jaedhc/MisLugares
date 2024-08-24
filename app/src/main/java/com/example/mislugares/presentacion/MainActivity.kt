package com.example.mislugares.presentacion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.mislugares.Aplicacion
import com.example.mislugares.casos_uso.CasosUsoActividades
import com.example.mislugares.casos_uso.CasosUsoLugar
import com.example.mislugares.databinding.ActivityMainBinding
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val lugares by lazy {(application as Aplicacion).lugares}
    val adaptador by lazy { (application as Aplicacion).adaptador }
    val usoLugar by lazy { CasosUsoLugar(this, lugares, adaptador) }
    val usoActividad by lazy { CasosUsoActividades(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val i = Intent(this, MainActivity2::class.java)
        //startActivity(i)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button01.setOnClickListener {
            usoActividad.lanzarLista()
        }

        binding.buttonBuscar.setOnClickListener{
            lanzarVistaLugar()
        }

        binding.button03.setOnClickListener {
            usoActividad.lanzarAcercaDe()
        }

        binding.button02.setOnClickListener {
            usoActividad.lanzarPreferencias()
        }

        binding.button04.setOnClickListener {
            finish()
        }
    }


    fun lanzarVistaLugar(view: View? = null){
       val entrada = EditText(this)
       entrada.setText("0")
       AlertDialog.Builder(this)
           .setTitle("Seleccion de lugar")
           .setMessage("indica su id: ")
           .setView(entrada)
           .setPositiveButton("Ok") {
               dialog, wichButton ->
               val id = parseInt(entrada.text.toString())
               usoLugar.mostrar(id)
           }
           .setNegativeButton("Cancelar", null)
           .show()
    }


}