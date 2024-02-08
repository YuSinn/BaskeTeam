package com.alexandru.vladut.gabriel97.basketeam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Equipo

@Dao
interface EquipoDao {

    // Inserta un equipo en la base de datos
    @Insert
    suspend fun insertEquipo(equipo: Equipo)

    // Obtiene todos los equipos de la base de datos
    @Query("SELECT * FROM equipos")
    suspend fun getEquipos(): List<Equipo>

    // Obtiene un equipo por su nombre
    @Query("SELECT * FROM equipos WHERE nombreEquipo = :nombre")
    suspend fun getEquipoByNombre(nombre: String): Equipo

    // Elimina un equipo de la base de datos
    @Delete
    suspend fun deleteEquipo(equipo: Equipo)
}