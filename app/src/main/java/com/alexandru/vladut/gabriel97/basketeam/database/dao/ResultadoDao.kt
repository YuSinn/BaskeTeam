package com.alexandru.vladut.gabriel97.basketeam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alexandru.vladut.gabriel97.basketeam.database.entities.Resultado

@Dao
interface ResultadoDao {

    // Inserta un resultado en la base de datos
    @Insert
    suspend fun insertResultado(resultado: Resultado)

    // Obtiene todos los resultados de la base de datos
    @Query("SELECT * FROM resultados")
    suspend fun getResultados(): List<Resultado>

    // Elimina un resultado de la base de datos
    @Delete
    suspend fun deleteResultado(resultado: Resultado)
}