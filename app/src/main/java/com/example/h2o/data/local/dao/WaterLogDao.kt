package com.example.h2o.data.local.dao

import androidx.room.*
import com.example.h2o.data.local.models.WaterLogData
import com.example.h2o.domain.models.WaterLog

@Dao
internal interface WaterLogDao {

    @Query("SELECT * FROM water_log")
    suspend fun getWaterLogList(): List<WaterLogData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLogData: WaterLogData)

    @Query("UPDATE water_log SET waterDrunk = waterDrunk - -:increment WHERE date=date('now', 'localtime')")
    suspend fun updateWaterProgress(increment: Int)

    @Query("SELECT * FROM water_log WHERE date = date('now', 'localtime')")
    suspend fun getWaterLog(): WaterLogData

    @Query("SELECT * FROM water_log WHERE date BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime')")
    suspend fun getTheLast7WaterLogs(): List<WaterLogData>

}