package com.example.h2o.domain.usecase

import com.example.h2o.domain.repository.SettingsRepository

class GetCurrentWaterValueFromCache(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getCurrentWaterValue()
}