package com.example.h2o.domain.usecase

import com.example.h2o.domain.models.WaterLog
import com.example.h2o.domain.repository.WaterLogRepository

class SaveWaterLogToLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(waterLog: WaterLog) = repository.insertWaterLog(waterLog)
}