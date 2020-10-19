package com.luisfelipe.h2o.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfelipe.h2o.domain.enums.WaterAction
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.usecase.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache,
    private val getWaterLogFromLocalDb: GetWaterLogFromLocalDb,
    private val saveWaterLogToLocalDb: SaveWaterLogToLocalDb,
    private val updateWaterFromLocalDb: UpdateWaterFromLocalDb
) : ViewModel() {

    private val waterLogLiveData = MutableLiveData<WaterLog>()
    val waterLog: LiveData<WaterLog> = waterLogLiveData

    private val cantRemoveWaterLiveData = MutableLiveData<Unit>()
    val cantRemoveWater: LiveData<Unit> = cantRemoveWaterLiveData

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()

    fun fetchWaterLogFromLocalDb() {
        viewModelScope.launch {
            val waterLog = getWaterLogFromLocalDb() ?: createEmptyWaterLog()
            waterLogLiveData.postValue(waterLog)
        }
    }

    fun updateWater(inputValue: String, action: WaterAction) {
        viewModelScope.launch {
            val realWaterProgress = getRealWaterProgress(inputValue)
            handleUpdateWaterTask(action, realWaterProgress)
        }
    }

    private suspend fun handleUpdateWaterTask(action: WaterAction, realWaterProgress: Int) {
        when (action) {
            WaterAction.ADD -> {
                updateWaterFromLocalDb(-realWaterProgress)
                updateWaterLogLiveData()
            }
            WaterAction.REMOVE -> {
                if (checkIfCanRemoveWater(realWaterProgress)) {
                    updateWaterFromLocalDb(realWaterProgress)
                    updateWaterLogLiveData()
                } else cantRemoveWaterLiveData.postValue(Unit)
            }
        }
    }

    private suspend fun updateWaterLogLiveData() {
        val waterLog = getWaterLogFromLocalDb()
        waterLogLiveData.postValue(waterLog)
    }

    private suspend fun checkIfCanRemoveWater(realWaterProgress: Int): Boolean {
        var canRemoveWater = false
        val waterLog = getWaterLogFromLocalDb()
        waterLog?.let { canRemoveWater = it.progress - realWaterProgress >= 0 }
        return canRemoveWater
    }

    private fun getRealWaterProgress(progress: String) =
        progress.removeSuffix("ml").toInt() / 100

    private suspend fun createEmptyWaterLog(): WaterLog {
        val waterLog = WaterLog(
            goalOfTheDay = fetchGoalOfTheDayFromCache(),
            progress = 0,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
        saveWaterLogToLocalDb(waterLog)

        return waterLog
    }
}