package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.SettingsRepository

class GetGoalOfTheDayFromCache(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getGoalOfTheDay()
}