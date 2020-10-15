package com.example.h2o.domain.usecase

import com.example.h2o.domain.repository.WaterLogRepository

class UpdateWaterLocalFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(waterProgress: Int) = repository.updateWaterProgressFromLocalDb(waterProgress)
}