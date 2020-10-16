package com.example.h2o.domain.usecase

import com.example.h2o.domain.repository.WaterLogRepository

class UpdateGoalOfTheDayFromLocalDb(private val repository: WaterLogRepository) {
    suspend operator fun invoke(goal: Int) = repository.updateGoalOfTheDayFromLocalDb(goal)
}