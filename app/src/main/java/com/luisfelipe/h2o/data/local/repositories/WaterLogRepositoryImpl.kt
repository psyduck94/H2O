package com.luisfelipe.h2o.data.local.repositories

import com.luisfelipe.h2o.data.local.dao.WaterLogDao
import com.luisfelipe.h2o.data.local.mapper.WaterLogMapper
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.repository.WaterLogRepository

internal class WaterLogRepositoryImpl(private val waterLogDao: WaterLogDao): WaterLogRepository {

    override suspend fun updateWaterProgressFromLocalDb(waterDrunk: Int) {
        waterLogDao.updateWaterProgress(waterDrunk)
    }

    override suspend fun getWaterLogFromLocalDb(): WaterLog? {
        val waterLogData = waterLogDao.getWaterLog()
        return WaterLogMapper.mapLocalToDomain(waterLogData)
    }

    override suspend fun getWaterLogListFromLocalDb(): List<WaterLog> {
        return WaterLogMapper.mapLocalToDomain(waterLogDao.getWaterLogList())
    }

    override suspend fun insertWaterLog(waterLog: WaterLog) {
        waterLogDao.insertWaterLog(WaterLogMapper.mapDomainToLocal(waterLog))
    }

    override suspend fun getTheLast7WaterlogsFromLocalDb(): List<WaterLog> {
        return WaterLogMapper.mapLocalToDomain(waterLogDao.getTheLast7WaterLogs())
    }

    override suspend fun updateGoalOfTheDayFromLocalDb(goalOfTheDay: Int) {
        waterLogDao.updateGoalOfTheDay(goalOfTheDay)
    }

}