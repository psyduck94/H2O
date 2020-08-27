package com.example.h2o.data

import com.example.h2o.data.local.SettingsCache
import com.example.h2o.domain.repository.SettingsRepository

class SettingsRepositoryImpl(private val settingsCache: SettingsCache) : SettingsRepository {

    override fun getGoalOfTheDay() = settingsCache.getWaterGoalOfTheDay()

    override fun setGoalOfTheDay(goal: Int) = settingsCache.setWaterGoalOfTheDay(goal)

}