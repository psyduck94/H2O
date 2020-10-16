package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.repository.WaterLogRepository

class SaveWaterLogToLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(waterLog: WaterLog) = repository.insertWaterLog(waterLog)
}