package com.alexandru.vladut.gabriel97.basketeam.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores",
    foreignKeys = [ForeignKey(
        entity = Equipo::class,
        parentColumns = ["nombreEquipo"],
        childColumns = ["nombreEquipoFK"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("nombreEquipoFK")])
data class Jugador(
    @PrimaryKey
    val nombre: String,
    val dorsal: Int,
    val posicion: String,
    val nombreEquipoFK: String
)