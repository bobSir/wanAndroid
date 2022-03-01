package com.bob.common.database.dao

import androidx.annotation.WorkerThread
import androidx.room.*
import com.bob.common.database.entity.NetCache

/**
 * created by cly on 2022/2/7
 */
@Dao
interface NetCacheDao {

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponseResult(netCache: NetCache): Long

    @WorkerThread
    @Transaction
    @Query("SELECT * FROM net_cache WHERE `key` = :key")
    suspend fun getResponseResult(key: String): NetCache

}