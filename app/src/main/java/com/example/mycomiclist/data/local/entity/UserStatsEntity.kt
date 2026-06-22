package com.example.mycomiclist.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycomiclist.domain.model.UserStats

@Entity(tableName = "user_stats") // Nombre de la tabla local
data class UserStatsEntity(
    @PrimaryKey val id: String, // Usamos el UID de Firebase como clave única
    @ColumnInfo(name = "last_connection") val lastConnection: String,
    @ColumnInfo(name = "total_read_comics") val totalReadComics: Int,
    @ColumnInfo(name = "favorite_genre") val favoriteGenre: String
)

// 🌟 MAPPERS DE LOS APUNTES: Traductores entre capas (Pág 42, 49)
fun UserStatsEntity.toDomain() = UserStats(
    id = id,
    lastConnection = lastConnection,
    totalReadComics = totalReadComics,
    favoriteGenre = favoriteGenre
)

fun UserStats.toEntity() = UserStatsEntity(
    id = id,
    lastConnection = lastConnection,
    totalReadComics = totalReadComics,
    favoriteGenre = favoriteGenre
)