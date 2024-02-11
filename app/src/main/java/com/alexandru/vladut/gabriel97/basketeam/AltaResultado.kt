package com.alexandru.vladut.gabriel97.basketeam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.alexandru.vladut.gabriel97.basketeam.adapters.CustomerAdapterJugador
import com.alexandru.vladut.gabriel97.basketeam.database.BasketDataBase
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Clasificacion
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Resultado
import com.alexandru.vladut.gabriel97.basketeam.dialogs.AltaEquipoDialog
import com.alexandru.vladut.gabriel97.basketeam.dialogs.AltaResultadoDialog
import kotlinx.coroutines.launch

class AltaResultado : AppCompatActivity() {

    var nuevoResultado : Resultado?=null
    var ganador : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta_resultado)
    }

    suspend fun databaseAccess() {
        val db = Room.databaseBuilder(
            applicationContext,
            BasketDataBase::class.java, "DDBB"
        ).build()
        val clasificacionDao = db.clasificacionDao()
        val resultadoDao = db.resultadoDao()
        try{
            nuevoResultado?.let { resultadoDao.insertResultado(it) }
            var equipoGanador: Clasificacion = clasificacionDao.getClasificacionByEquipo(ganador)
            var puntos = equipoGanador.puntos+2
            clasificacionDao.updateClasamentoById(equipoGanador.id,puntos)
            nuevoResultado=null
            ganador = ""
        }catch(e : Exception){
            Toast.makeText(this,"No se ha podido agregar el resultado", Toast.LENGTH_LONG).show()
        }


    }
    fun onAltaResultado(view : View){
        val miCustomDialog = AltaResultadoDialog()
        miCustomDialog.altaResultado = this
        miCustomDialog.show(supportFragmentManager, "AltaResultadoDialog")
        miCustomDialog.setOnDialogResultListener(object : AltaResultadoDialog.OnDialogResultListener {
            override fun onResult(equipoLocal:String,equipoVisitante:String,golesLocal:Int, golesVisitante:Int,fecha:String) {
                nuevoResultado = Resultado(0,equipoLocal,equipoVisitante,golesLocal, golesVisitante,fecha)
                if (golesLocal>golesVisitante){
                    ganador=equipoLocal
                }else{
                    ganador=equipoVisitante
                }
                lifecycleScope.launch {
                    databaseAccess()
                }

            }
        })
    }


}