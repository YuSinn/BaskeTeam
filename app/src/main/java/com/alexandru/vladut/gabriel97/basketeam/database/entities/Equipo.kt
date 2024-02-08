package com.alexandru.vladut.gabriel97.basketeam.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class Equipo(

    @PrimaryKey(autoGenerate = false)
    val nombreEquipo: String,
    val ciudad: String,
    val nombrePabellon: String
)