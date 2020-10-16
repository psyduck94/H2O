package com.example.h2o.presentation.archives

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h2o.domain.models.WaterLog
import com.example.h2o.domain.usecase.GetTheLast7WaterLogsFromLocalDb
import kotlinx.coroutines.launch

class ArchivesViewModel(
    private val getTheLast7WaterLogsFromLocalDb: GetTheLast7WaterLogsFromLocalDb
) : ViewModel() {

    private val waterLogListLiveData = MutableLiveData<List<WaterLog>>()
    val waterLogList: LiveData<List<WaterLog>> = waterLogListLiveData

    fun fetchTheLast7WaterLogsFromLocalDb() {
        viewModelScope.launch {
            val waterLogList = getTheLast7WaterLogsFromLocalDb()
            waterLogListLiveData.postValue(waterLogList)
        }
    }

}