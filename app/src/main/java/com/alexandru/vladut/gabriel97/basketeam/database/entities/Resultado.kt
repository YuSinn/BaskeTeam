package com.alexandru.vladut.gabriel97.basketeam.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "resultados",
    foreignKeys = [ForeignKey(
        entity = Equipo::class,
        parentColumns = ["nombreEquipo"],
        childColumns = ["equipoLocalFK"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Equipo::class,
        parentColumns = ["nombreEquipo"],
        childColumns = ["equipoVisitanteFK"],
        onDelete = ForeignKey.CASCADE
    )],indices = [Index("equipoLocalFK"), Index("equipoVisitanteFK")])
data class Resultado(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val equipoLocalFK: String,
    val equipoVisitanteFK: String,
    val golesLocal: Int,
    val golesVisitante: Int,
    val dia: String
)