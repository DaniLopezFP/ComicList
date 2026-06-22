package com.example.mycomiclist.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    val id: String = "",              // El UID de Firebase del usuario
    val lastConnection: String = "",   // Fecha/Hora de la última conexión
    val totalReadComics: Int = 0,     // Contador de cómics leídos
    val favoriteGenre: String = ""    // Género preferido
)