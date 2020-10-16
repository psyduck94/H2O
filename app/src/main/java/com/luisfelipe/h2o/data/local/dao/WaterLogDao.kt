package com.luisfelipe.h2o.data.local.dao

import androidx.room.*
import com.luisfelipe.h2o.data.local.models.WaterLogData

@Dao
internal interface WaterLogDao {

    @Query("SELECT * FROM water_log")
    suspend fun getWaterLogList(): List<WaterLogData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLogData: WaterLogData)

    @Query("UPDATE water_log SET waterDrunk = waterDrunk -(:water) WHERE date=date('now', 'localtime')")
    suspend fun updateWater(water: Int)

    @Query("UPDATE water_log SET waterNeed = :waterNeed WHERE date=date('now', 'localtime')")
    suspend fun updateGoalOfTheDay(waterNeed: Int)

    @Query("SELECT * FROM water_log WHERE date = date('now', 'localtime')")
    suspend fun getWaterLog(): WaterLogData

    @Query("SELECT * FROM water_log WHERE date BETWEEN datetime('now', '-6 days') AND datetime('now', 'localtime') ORDER BY date DESC")
    suspend fun getTheLast7WaterLogs(): List<WaterLogData>

}