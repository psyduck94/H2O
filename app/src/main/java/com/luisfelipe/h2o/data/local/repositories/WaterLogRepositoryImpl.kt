package com.luisfelipe.h2o.data.local.repositories

import com.luisfelipe.h2o.data.local.dao.WaterLogDao
import com.luisfelipe.h2o.data.local.mapper.WaterLogMapper
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.repository.WaterLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class WaterLogRepositoryImpl(private val waterLogDao: WaterLogDao) : WaterLogRepository {

    override suspend fun updateWaterFromLocalDb(water: Int) = withContext(Dispatchers.IO) {
        waterLogDao.updateWater(water)
    }

    override suspend fun getWaterLogFromLocalDb(): WaterLog? = withContext(Dispatchers.IO) {
        val waterLogData = waterLogDao.getWaterLog()
        return@withContext WaterLogMapper.mapLocalToDomain(waterLogData)
    }

    override suspend fun getWaterLogListFromLocalDb(): List<WaterLog> =
        withContext(Dispatchers.IO) {
            return@withContext WaterLogMapper.mapLocalToDomain(waterLogDao.getWaterLogList())
        }

    override suspend fun insertWaterLog(waterLog: WaterLog) = withContext(Dispatchers.IO) {
        waterLogDao.insertWaterLog(WaterLogMapper.mapDomainToLocal(waterLog))
    }

    override suspend fun getTheLast7WaterlogsFromLocalDb(): List<WaterLog> =
        withContext(Dispatchers.IO) {
            return@withContext WaterLogMapper.mapLocalToDomain(waterLogDao.getTheLast7WaterLogs())
        }

    override suspend fun updateGoalOfTheDayFromLocalDb(goalOfTheDay: Int) =
        withContext(Dispatchers.IO) {
            waterLogDao.updateGoalOfTheDay(goalOfTheDay)
        }

}