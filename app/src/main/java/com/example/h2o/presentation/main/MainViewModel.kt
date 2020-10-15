package com.example.h2o.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h2o.domain.models.WaterLog
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.example.h2o.domain.usecase.GetWaterLogFromLocalDb
import com.example.h2o.domain.usecase.SaveWaterLogToLocalDb
import com.example.h2o.domain.usecase.UpdateWaterLocalFromLocalDb
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache,
    private val getWaterLogFromLocalDb: GetWaterLogFromLocalDb,
    private val saveWaterLogToLocalDb: SaveWaterLogToLocalDb,
    private val updateWaterLogFromLocalDb: UpdateWaterLocalFromLocalDb
) : ViewModel() {

    private val waterLogLiveData = MutableLiveData<WaterLog>()
    val waterLog: LiveData<WaterLog> = waterLogLiveData

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()

    fun fetchWaterLogFromLocalDb() {
        viewModelScope.launch {
            val waterLog = getWaterLogFromLocalDb() ?: createEmptyWaterLog()
            waterLogLiveData.postValue(waterLog)
        }
    }

    fun updateWaterProgress(progress: String) {
        viewModelScope.launch {
            val realWaterProgress = progress.removeSuffix("ml").toInt()/100
            updateWaterLogFromLocalDb(realWaterProgress)
            val waterLog = getWaterLogFromLocalDb()
            waterLog?.let { it.progress = realWaterProgress }
            waterLogLiveData.postValue(waterLog)
        }
    }

    private fun createEmptyWaterLog(): WaterLog {
        val waterLog = WaterLog(
            goalOfTheDay = fetchGoalOfTheDayFromCache(),
            progress = 0,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
        viewModelScope.launch {
            saveWaterLogToLocalDb(waterLog)
        }
        return waterLog
    }
}