package com.alexandru.vladut.gabriel97.basketeam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterJugador
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador
import com.alexandru.vladut.gabriel97.basketeam.dialogs.AltaJugadorDialog
import com.alexandru.vladut.gabriel97.basketeam.dialogs.EditarJugadorDialog
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class EditarEquipo : AppCompatActivity(),CustomerAdapterJugador.OnClickListener {
    var nombreEquipo=""
    var listaJugador : List<Jugador>? = null
    var nuevoJugador : Jugador? = null
    var jugadorToBorrar:Jugador? = null
    var jugadorToEditar:Jugador? = null
    var jugadorActualAborrar:Jugador? = null
    lateinit var recyclerJugadores: RecyclerView
    lateinit var borrar : RadioButton
    lateinit var editar : RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_equipo)

         nombreEquipo = intent.getStringExtra("nombreEquipo").toString()
        var titulo = findViewById<TextView>(R.id.titulo)
        titulo.text=nombreEquipo
        lifecycleScope.launch {
            databaseAccess()
        }
    }

    suspend fun databaseAccess() {
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()
        val equipoDao = db.equipoDao()
        val jugadorDao = db.jugadorDao()

        try {
            nuevoJugador?.let{jugadorDao.insertJugador(it)}
            // Inserción exitosa
        } catch (e: Exception) {
            Toast.makeText(this, "No se puede crear el jugador!", Toast.LENGTH_SHORT).show()
        }
        try {

            jugadorToEditar?.let{jugadorDao.insertJugador(it)}
            jugadorActualAborrar?.let { jugadorDao.deleteJugador(it) }
            // Inserción exitosa
        } catch (e: Exception) {
            Toast.makeText(this, "No se puede editar el jugador!", Toast.LENGTH_SHORT).show()
        }
        try{
            listaJugador=jugadorDao.getJugadoresByEquipo(nombreEquipo)
            recyclerJugadores= findViewById(R.id.recyclerJugadores)

            var jugadorAdapter = listaJugador?.let { CustomerAdapterJugador(it) }
            jugadorAdapter!!.setOnItemClickListener(this)
            recyclerJugadores.layoutManager = LinearLayoutManager(this)
            recyclerJugadores.adapter = jugadorAdapter

        }catch (e :Exception){
            Toast.makeText(this, "No se puede mostrar!", Toast.LENGTH_SHORT).show()
        }

        try{
            jugadorToBorrar?.let { jugadorDao.deleteJugador(it) }
            jugadorToBorrar=null
        }catch (e:Exception){
            Toast.makeText(this, "No se pudo eleminar!", Toast.LENGTH_SHORT).show()
        }
    }
    fun onAltaJugadorDialogRadioButton(view: View){
        val miCustomDialog = AltaJugadorDialog()
        miCustomDialog.editarEquipo = this
        miCustomDialog.show(supportFragmentManager, "AltaJugadorDialog")
        miCustomDialog.setOnDialogResultListener(object : AltaJugadorDialog.OnDialogJugadorResultListener {
            override fun onResult(nombre:String,dorsal:Int,posicion:String,equipo: String) {
                nuevoJugador = crearJugador(nombre, dorsal, posicion,nombreEquipo)
                lifecycleScope.launch {
                    databaseAccess()
                }
                nuevoJugador = null
            }
        })
    }
    override fun onClick(jugador: Jugador){
        Toast.makeText(this,"¡Para eliminar tienes seleccionar el radio de borrar y darle 2!",Toast.LENGTH_LONG).show()
        borrar = findViewById(R.id.radioButtonBorrar)
        editar =findViewById(R.id.radioButtonEditar)
        if(borrar.isChecked){
            jugadorToBorrar=jugador
            lifecycleScope.launch {
                databaseAccess()
            }
        }else if(editar.isChecked){
            val miCustomDialog = EditarJugadorDialog()
            jugadorToEditar = jugador
            jugadorActualAborrar=jugador
            miCustomDialog.editarEquipo = this
            miCustomDialog.show(supportFragmentManager, "EditarJugadorDialog")
            miCustomDialog.setOnDialogResultListener(object : EditarJugadorDialog.OnDialogResultListener {
                override fun onResult(nombre: String,dorsal:Int,posicion:String) {
                    if (dorsal != null) {
                        // Caso 1: Dorsal no es nulo
                        if (!posicion.isBlank() && !nombre.isBlank()) {
                            // Caso 1.1: Dorsal no es nulo, posición y equipo no están en blanco
                            jugadorToEditar = crearJugador(nombre, dorsal, posicion, jugador.nombreEquipoFK)
                        } else if (!posicion.isBlank()) {
                            // Caso 1.2: Dorsal no es nulo, posición no está en blanco pero nombre está en blanco
                            jugadorToEditar = crearJugador(jugador.nombre, dorsal, posicion, jugador.nombreEquipoFK)
                        } else if (!nombre.isBlank()) {
                            // Caso 1.3: Dorsal no es nulo, nombre no está en blanco pero posición está en blanco
                            jugadorToEditar = crearJugador(nombre, dorsal, jugador.posicion, jugador.nombreEquipoFK)
                        } else {
                            // Caso 1.4: Dorsal no es nulo, tanto posición como equipo están en blanco
                            jugadorToEditar = crearJugador(jugador.nombre, dorsal, jugador.posicion, jugador.nombreEquipoFK)
                        }
                    } else {
                        // Caso 2: Dorsal es nulo
                        if (!posicion.isBlank() && !nombre.isBlank()) {
                            // Caso 2.1: Dorsal es nulo, posición y nombre no están en blanco
                            jugadorToEditar = crearJugador(nombre, jugador.dorsal, posicion, jugador.nombreEquipoFK)
                        } else if (!posicion.isBlank()) {
                            // Caso 2.2: Dorsal es nulo, posición no está en blanco pero equipo está en blanco
                            jugadorToEditar = crearJugador(jugador.nombre, jugador.dorsal, posicion, jugador.nombreEquipoFK)
                        } else if (!nombre.isBlank()) {
                            // Caso 2.3: Dorsal es nulo, nombre no está en blanco pero posición está en blanco
                            jugadorToEditar = crearJugador(nombre, jugador.dorsal, jugador.posicion, jugador.nombreEquipoFK)
                        } else {
                            // Caso 2.4: Dorsal es nulo, tanto posición como equipo están en blanco
                            jugadorToEditar = crearJugador(jugador.nombre, jugador.dorsal, jugador.posicion, jugador.nombreEquipoFK)
                        }
                    }
                    lifecycleScope.launch {
                        databaseAccess()
                    }
                    jugadorToEditar = null
                }
            })
        }
    }
    fun crearJugador(nombre:String,dorsal:Int,posicion:String,equipo:String):Jugador{
        return Jugador(nombre,dorsal,posicion,equipo)
    }
}