package com.example.h2o.data.local.dao

import androidx.room.*
import com.example.h2o.data.local.models.WaterLogData

@Dao
internal interface WaterLogDao {

    @Query("SELECT * FROM water_log")
    suspend fun getWaterLogList(): List<WaterLogData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterLog(waterLogData: WaterLogData)

    @Query("UPDATE water_log SET waterDrunk = waterDrunk - -:increment WHERE currentDate=date('now')")
    suspend fun updateWaterProgress(increment: Int)

    @Query("SELECT * FROM water_log WHERE currentDate = date('now')")
    suspend fun getWaterLog(): WaterLogData

}