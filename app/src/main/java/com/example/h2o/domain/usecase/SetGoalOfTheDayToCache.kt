package com.example.h2o.domain.usecase

import com.example.h2o.domain.repository.SettingsRepository

class SetGoalOfTheDayToCache(private val repository: SettingsRepository) {
    operator fun invoke(goal: Int) = repository.setGoalOfTheDay(goal)
}