package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.WaterLogRepository

class GetWaterLogFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke() = repository.getWaterLogFromLocalDb()
}