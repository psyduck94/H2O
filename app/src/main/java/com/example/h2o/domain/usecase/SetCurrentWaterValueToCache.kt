package com.example.h2o.domain.usecase

import com.example.h2o.domain.repository.SettingsRepository

class SetCurrentWaterValueToCache(private val repository: SettingsRepository) {
    operator fun invoke(value: Int) = repository.setCurrentWaterValue(value)
}