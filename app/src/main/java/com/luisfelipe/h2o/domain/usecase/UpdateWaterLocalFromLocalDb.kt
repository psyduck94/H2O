package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.WaterLogRepository

class UpdateWaterLocalFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(waterProgress: Int) = repository.updateWaterProgressFromLocalDb(waterProgress)
}