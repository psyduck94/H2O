package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.WaterLogRepository

class UpdateGoalOfTheDayFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(goal: Int) = repository.updateGoalOfTheDayFromLocalDb(goal)
}