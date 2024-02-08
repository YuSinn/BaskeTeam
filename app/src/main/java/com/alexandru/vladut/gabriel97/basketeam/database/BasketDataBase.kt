package com.alexandru.vladut.gabriel97.basketeam.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexandru.vladut.gabriel97.basketeam.database.dao.ClasificacionDao
import com.alexandru.vladut.gabriel97.basketeam.database.dao.EquipoDao
import com.alexandru.vladut.gabriel97.basketeam.database.dao.JugadorDao
import com.alexandru.vladut.gabriel97.basketeam.database.dao.ResultadoDao
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Clasificacion
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Resultado

@Database(entities = [Equipo::class, Jugador::class, Resultado::class, Clasificacion::class], version = 1,  exportSchema = true)
abstract class BasketDataBase : RoomDatabase() {
    abstract fun equipoDao(): EquipoDao
    abstract fun jugadorDao(): JugadorDao
    abstract fun resultadoDao(): ResultadoDao
    abstract fun clasificacionDao(): ClasificacionDao
}