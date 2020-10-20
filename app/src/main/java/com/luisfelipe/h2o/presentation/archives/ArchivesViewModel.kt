package com.luisfelipe.h2o.presentation.archives

import androidx.lifecycle.*
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.usecase.GetTheLast7WaterLogsFromLocalDb
import kotlinx.coroutines.launch

class ArchivesViewModel(
    private val getTheLast7WaterLogsFromLocalDb: GetTheLast7WaterLogsFromLocalDb
) : ViewModel() {

    val waterLogList = liveData {
        val result = getTheLast7WaterLogsFromLocalDb()
        emit(result)
    }

}