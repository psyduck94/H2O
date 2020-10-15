package com.example.h2o.domain.repository

import com.example.h2o.domain.models.WaterLog

interface WaterLogRepository {
    suspend fun updateWaterProgressFromLocalDb(waterDrunk: Int)
    suspend fun getWaterLogFromLocalDb(): WaterLog?
    suspend fun getWaterLogListFromLocalDb(): List<WaterLog>
    suspend fun insertWaterLog(waterLog: WaterLog)
}