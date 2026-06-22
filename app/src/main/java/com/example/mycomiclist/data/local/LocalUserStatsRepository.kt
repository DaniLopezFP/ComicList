package com.example.mycomiclist.data.local

import com.example.mycomiclist.data.local.dao.UserStatsDao
import com.example.mycomiclist.data.local.entity.toDomain
import com.example.mycomiclist.data.local.entity.toEntity
import com.example.mycomiclist.domain.model.UserStats
import com.example.mycomiclist.domain.repository.UserStatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserStatsRepository(
    private val userStatsDao: UserStatsDao // Inyectamos el DAO (Pág 51)
) : UserStatsRepository {

    override fun getUserStatsByUser(userId: String): Flow<UserStats> {
        // Mapeamos el flujo de salida de la entidad al modelo limpio del Domain (Pág 51)
        return userStatsDao.getUserStatsByUserStream(userId).map { it.toDomain() }
    }

    override suspend fun addUserStats(stats: UserStats) {
        userStatsDao.insertUserStats(stats.toEntity()) // Mapeo hacia la base de datos (Pág 51)
    }

    override suspend fun updateUserStats(stats: UserStats) {
        userStatsDao.updateUserStats(stats.toEntity())
    }

    override suspend fun deleteUserStats(stats: UserStats) {
        userStatsDao.deleteUserStats(stats.toEntity())
    }
}