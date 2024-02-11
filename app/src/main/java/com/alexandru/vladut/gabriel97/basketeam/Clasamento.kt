package com.alexandru.vladut.gabriel97.basketeam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.alexandru.vladut.gabriel97.basketeam.R
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterClasificacion
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterJugador
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Clasificacion
import kotlinx.coroutines.launch

class Clasamento : AppCompatActivity() {
    lateinit var recyclerClasamento : RecyclerView
    var listaEquipoClasamento : List<Clasificacion>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clasamento)
        lifecycleScope.launch {
            databaseAccess()
        }
    }

    suspend fun databaseAccess() {
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()

        val clasificacionDao = db.clasificacionDao()

        try{
            listaEquipoClasamento=clasificacionDao.getAllOrderedByPuntosDesc()
            recyclerClasamento= findViewById(R.id.recyclerClasamento)
            var clasificacionAdapter = listaEquipoClasamento?.let { CustomerAdapterClasificacion(it) }
            recyclerClasamento.layoutManager = LinearLayoutManager(this)
            recyclerClasamento.adapter = clasificacionAdapter

        }catch (e :Exception){
            Toast.makeText(this, "No se puede mostrar!", Toast.LENGTH_SHORT).show()
        }
    }
}