package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.WaterLogRepository

class UpdateWaterFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(water: Int) = repository.updateWaterFromLocalDb(water)
}