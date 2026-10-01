package com.citadela.multiverseradar.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Modelo LOCAL: tabela do Room. É o único modelo que a UI enxerga. */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val species: String,
    val status: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)
