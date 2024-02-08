package com.alexandru.vladut.gabriel97.basketeam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Jugador

@Dao
interface JugadorDao {

    // Inserta un jugador en la base de datos
    @Insert
    suspend fun insertJugador(jugador: Jugador)

    // Obtiene todos los jugadores de un equipo por su nombre
    @Query("SELECT * FROM jugadores WHERE nombreEquipoFK = :nombreEquipo")
    suspend fun getJugadoresByEquipo(nombreEquipo: String): List<Jugador>

    // Elimina un jugador de la base de datos
    @Delete
    suspend fun deleteJugador(jugador: Jugador)
}