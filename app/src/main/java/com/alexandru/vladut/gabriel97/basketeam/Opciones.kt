package com.alexandru.vladut.gabriel97.basketeam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Opciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

    }

    fun onAltaEq(view: View){
        val intent = Intent(this, AltaEquipo::class.java)
        startActivity(intent)
    }
    fun onAltaResultado(view: View){
        val intent = Intent(this, AltaResultado::class.java)
        startActivity(intent)
    }

    fun onClasamento(view: View){
        val intent = Intent(this, Clasamento::class.java)
        startActivity(intent)
    }

    fun onListado(view: View){
        val intent = Intent(this, ListadoEquipos::class.java)
        startActivity(intent)
    }
}