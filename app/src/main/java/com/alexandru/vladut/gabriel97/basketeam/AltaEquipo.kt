package com.alexandru.vladut.gabriel97.basketeam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import kotlinx.coroutines.launch

class AltaEquipo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta_equipo)

        lifecycleScope.launch {
            databaseAccess()
        }
    }

    suspend fun databaseAccess(){
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()


        val equipoDao = db.equipoDao()

        var equipo = Equipo("Laakers","New York", "Metropolitan")
        //equipoDao.insertEquipo(equipo)
        val equipos: List<Equipo> = equipoDao.getEquipos()

        val i = 0
    }
}