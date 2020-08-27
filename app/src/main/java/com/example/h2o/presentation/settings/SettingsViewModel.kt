package com.example.h2o.presentation.settings

import androidx.lifecycle.ViewModel
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.example.h2o.domain.usecase.SetGoalOfTheDayToCache

class SettingsViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache,
    private val setGoalOfTheDayToCache: SetGoalOfTheDayToCache
) : ViewModel() {

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()
    fun saveGoalOfTheDayFromCache(goal: Int) = setGoalOfTheDayToCache(goal)
}