package com.luisfelipe.h2o.domain.repository

import com.luisfelipe.h2o.domain.models.WaterLog

interface WaterLogRepository {
    suspend fun updateWaterProgressFromLocalDb(waterDrunk: Int)
    suspend fun getWaterLogFromLocalDb(): WaterLog?
    suspend fun getWaterLogListFromLocalDb(): List<WaterLog>
    suspend fun insertWaterLog(waterLog: WaterLog)
    suspend fun getTheLast7WaterlogsFromLocalDb(): List<WaterLog>
    suspend fun updateGoalOfTheDayFromLocalDb(goalOfTheDay: Int)
}