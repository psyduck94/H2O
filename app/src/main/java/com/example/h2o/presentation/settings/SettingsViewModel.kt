package com.example.h2o.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h2o.domain.enums.InputState
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.example.h2o.domain.usecase.SetGoalOfTheDayToCache
import com.example.h2o.domain.usecase.UpdateGoalOfTheDayFromLocalDb
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache,
    private val setGoalOfTheDayToCache: SetGoalOfTheDayToCache,
    private val updateGoalOfTheDayFromLocalDb: UpdateGoalOfTheDayFromLocalDb
) : ViewModel() {

    private companion object {
        const val MAX_GOAL = 10000
        const val PERCENT = 100
    }

    private val goalOfTheDayInputStateLiveData = MutableLiveData<InputState>()
    val goalOfTheDayInputState: LiveData<InputState> = goalOfTheDayInputStateLiveData

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()*PERCENT

    fun saveGoalOfTheDayToCache(goal: String) {
        when {
            goal.isEmpty() -> goalOfTheDayInputStateLiveData.postValue(InputState.EMPTY)
            goal.toInt() > MAX_GOAL -> goalOfTheDayInputStateLiveData.postValue(InputState.INVALID)
            else -> {
                goalOfTheDayInputStateLiveData.postValue(InputState.VALID)
                setGoalOfTheDayToCache(goal.toInt()/PERCENT)
            }
        }
    }

    fun updateGoalOfTheDayFromLocalDatabase(goal: Int) {
        viewModelScope.launch {
            updateGoalOfTheDayFromLocalDb(goal)
        }
    }
}