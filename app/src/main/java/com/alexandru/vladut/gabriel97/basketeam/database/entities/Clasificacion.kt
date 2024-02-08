package com.alexandru.vladut.gabriel97.basketeam.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "clasificaciones",
    foreignKeys = [ForeignKey(
        entity = Equipo::class,
        parentColumns = ["nombreEquipo"],
        childColumns = ["nombreEquipoFK"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("nombreEquipoFK")])
data class Clasificacion(
    @PrimaryKey val id: Int,
    val puntos: Int,
    val nombreEquipoFK: String
)