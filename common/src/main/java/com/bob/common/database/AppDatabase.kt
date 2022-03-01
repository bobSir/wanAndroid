package com.bob.common.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bob.common.database.entity.NetCache
import com.bob.common.util.ContextUtil

/**
 * created by cly on 2022/2/7
 */
@Database(
    entities = [NetCache::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "wan_android"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ContextUtil.getContext().applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}