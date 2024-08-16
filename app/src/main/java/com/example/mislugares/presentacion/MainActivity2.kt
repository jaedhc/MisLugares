package com.example.mislugares.presentacion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mislugares.Aplicacion
import com.example.mislugares.R
import com.example.mislugares.casos_uso.CasosUsoActividades
import com.example.mislugares.casos_uso.CasosUsoLugar

class MainActivity2 : AppCompatActivity() {

    val lugares by lazy {(application as Aplicacion).lugares}
    val adaptador by lazy { (application as Aplicacion).adaptador }
    val usoLugar by lazy { CasosUsoLugar(this, lugares) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //lugares.añadeEjemplos()

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adaptador
        }

    }

}