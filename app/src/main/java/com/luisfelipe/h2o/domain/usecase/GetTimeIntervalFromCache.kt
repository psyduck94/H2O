package com.luisfelipe.h2o.domain.usecase

import com.luisfelipe.h2o.domain.repository.SettingsRepository

class GetTimeIntervalFromCache(private val repository: SettingsRepository) {
    operator fun invoke() = repository.getTimeIntervalFromCache()
}