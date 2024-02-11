package com.alexandru.vladut.gabriel97.basketeam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterJugador
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Clasificacion
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador
import com.alexandru.vladut.gabriel97.basketeam.dialogs.AltaEquipoDialog
import com.alexandru.vladut.gabriel97.basketeam.dialogs.AltaJugadorDialog
import com.alexandru.vladut.gabriel97.basketeam.dialogs.ListaJugadorDialog
import kotlinx.coroutines.launch

class AltaEquipo : AppCompatActivity() {

    private var nuevoEquipo: Equipo? = null
    private var nuevoJugador: Jugador? = null
    private var listaJugador: List<Jugador>? =null
    private var equipoParaLista:String =""
    lateinit var recyclerViewJugador: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta_equipo)

        //lifecycleScope.launch {
        //    databaseAccess()
        //}
    }//

    suspend fun databaseAccess() {
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()
        val equipoDao = db.equipoDao()
        val jugadorDao = db.jugadorDao()
        val clasificacionDao = db.clasificacionDao()

        try {
            if(nuevoEquipo!=null){
            var clasificacion = Clasificacion(0,0,nuevoEquipo!!.nombreEquipo)
            nuevoEquipo?.let { equipoDao.insertEquipo(it) }
            clasificacionDao.insertClasificacion(clasificacion)
            }

        } catch (e: Exception){
            Toast.makeText(this, "No se puede crear el equipo!", Toast.LENGTH_LONG).show()
        }
        try {
            nuevoJugador?.let{jugadorDao.insertJugador(it)}
            // Inserción exitosa
        } catch (e: Exception) {
            Toast.makeText(this, "No se puede crear el jugador!", Toast.LENGTH_SHORT).show()
        }

        try{
            listaJugador=jugadorDao.getJugadoresByEquipo(equipoParaLista)
            recyclerViewJugador= findViewById(R.id.recyclerViewJugadores)
            var jugadorAdapter = listaJugador?.let { CustomerAdapterJugador(it) }
            recyclerViewJugador.layoutManager = LinearLayoutManager(this)
            recyclerViewJugador.adapter = jugadorAdapter

        }catch (e :Exception){
            Toast.makeText(this, "No se puede mostrar!", Toast.LENGTH_SHORT).show()
        }
    }

    fun onAltaEquipoDialogButton(view:View){
        val miCustomDialog = AltaEquipoDialog()
        miCustomDialog.altaEquipo = this
        miCustomDialog.show(supportFragmentManager, "AltaEquipoDialog")
        miCustomDialog.setOnDialogResultListener(object : AltaEquipoDialog.OnDialogResultListener {
            override fun onResult(nombre: String, ciudad: String, pabellon: String) {
                nuevoEquipo = crearEquipo(nombre, ciudad, pabellon)
                lifecycleScope.launch {
                    databaseAccess()
                }
                nuevoEquipo=null
            }
        })
    }
    fun onAltaJugadorDialogButton(view:View){
        val miCustomDialog = AltaJugadorDialog()
        miCustomDialog.altaEquipo = this
        miCustomDialog.show(supportFragmentManager, "AltaJugadorDialog")
        miCustomDialog.setOnDialogResultListener(object : AltaJugadorDialog.OnDialogJugadorResultListener {
            override fun onResult(nombre:String,dorsal:Int,posicion:String,equipo: String) {
                nuevoJugador = crearJugador(nombre, dorsal, posicion,equipo)
                lifecycleScope.launch {
                    databaseAccess()
                }
                nuevoJugador = null
            }
        })
    }

    fun onListaJugadoresButton(view: View){
        val miCustomDialog = ListaJugadorDialog()
        miCustomDialog.altaEquipo = this
        miCustomDialog.show(supportFragmentManager, "ListaJugadorDialog")
        miCustomDialog.setOnDialogResultListener(object : ListaJugadorDialog.OnDialogResultListener {
            override fun onResult(nombre:String) {
                equipoParaLista = nombre
                lifecycleScope.launch {
                    databaseAccess()
                }
                equipoParaLista=""
            }
        })
    }

    fun crearEquipo(nombre:String,ciudad:String,pabellon:String):Equipo{
        var equipo = Equipo(nombre, ciudad,pabellon)
        return equipo
    }
    fun crearJugador(nombre:String,dorsal:Int,posicion:String,equipo:String):Jugador{
        return Jugador(nombre,dorsal,posicion,equipo)
    }
}