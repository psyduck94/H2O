package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.SettingsRepository

class SetGoalOfTheDayToCache(private val repository: SettingsRepository) {
    operator fun invoke(goal: Int) = repository.setGoalOfTheDay(goal)
}