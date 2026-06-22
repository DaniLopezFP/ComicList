package com.example.mycomiclist.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycomiclist.data.local.dao.UserStatsDao
import com.example.mycomiclist.data.local.entity.UserStatsEntity

@Database(entities = [UserStatsEntity::class], version = 1, exportSchema = false)
abstract class UserStatsDatabase : RoomDatabase() {

    abstract fun userStatsDao(): UserStatsDao // Exponemos el DAO

    companion object {
        @Volatile
        private var Instance: UserStatsDatabase? = null // Singleton actualizado en memoria principal

        fun getDatabase(context: Context): UserStatsDatabase {
            // Evitamos condiciones de carrera con hilos simultáneos usando un bloque bloqueado
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    UserStatsDatabase::class.java,
                    "user_stats_database" // Nombre físico del fichero .db
                )
                    .fallbackToDestructiveMigration() // Si cambias el modelo, borra y recrea la estructura
                    .build()
                    .also { Instance = it } // Guardamos la referencia
            }
        }
    }
}