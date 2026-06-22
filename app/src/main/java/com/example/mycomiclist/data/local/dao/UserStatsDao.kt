package com.example.mycomiclist.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mycomiclist.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStatsDao {

    // Consulta reactiva usando flujos (Flow) para refrescar la pantalla solos
    @Query("SELECT * FROM user_stats WHERE id = :id")
    fun getUserStatsByUserStream(id: String): Flow<UserStatsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Evita crasheos duplicados
    suspend fun insertUserStats(stats: UserStatsEntity)

    @Update // Pág 28
    suspend fun updateUserStats(stats: UserStatsEntity)

    @Delete // Pág 29
    suspend fun deleteUserStats(stats: UserStatsEntity)
}