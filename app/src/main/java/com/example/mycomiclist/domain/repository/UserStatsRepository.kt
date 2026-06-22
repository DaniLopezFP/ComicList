package com.example.mycomiclist.domain.repository

import com.example.mycomiclist.domain.model.UserStats
import kotlinx.coroutines.flow.Flow

interface UserStatsRepository {
    // Escucha en tiempo real las estadísticas del usuario
    fun getUserStatsByUser(userId: String): Flow<UserStats>

    suspend fun addUserStats(stats: UserStats)
    suspend fun updateUserStats(stats: UserStats)
    suspend fun deleteUserStats(stats: UserStats)
}