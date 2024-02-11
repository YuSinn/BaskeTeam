package com.alexandru.vladut.gabriel97.basketeam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterEquipo
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterJugador
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import kotlinx.coroutines.launch

class ListadoEquipos : AppCompatActivity(), CustomerAdapterEquipo.OnClickListener{

    lateinit var searchEquipo: SearchView
    lateinit var listaEquipos: RecyclerView
    private var equipos :List<Equipo>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_equipos)

        searchEquipo = findViewById(R.id.searchEquipo)
        listaEquipos = findViewById(R.id.listaEquipos)

        searchEquipo.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String): Boolean {
               lifecycleScope.launch {
                    databaseAccess(newText)
                }
                return true
            }

        })
    }
    suspend fun databaseAccess(equipo:String) {
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()
        val equipoDao = db.equipoDao()

        try{
            equipos= equipoDao.getEquiposByNombre(equipo)

            var equipoAdapter = equipos?.let { CustomerAdapterEquipo(it) }
            equipoAdapter!!.setOnItemClickListener(this)
            listaEquipos.layoutManager = LinearLayoutManager(this)
            listaEquipos.adapter = equipoAdapter

        }catch (e :Exception){
            Toast.makeText(this, "No se puede mostrar!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onClick(equipo: Equipo) {
        // Mostrar un Toast con el nombre del equipo
        Toast.makeText(this, "Equipo seleccionado: ${equipo.nombreEquipo}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, EditarEquipo::class.java)
        intent.putExtra("nombreEquipo", equipo.nombreEquipo)
        startActivity(intent)
    }
}