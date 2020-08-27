package com.example.h2o.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache

class MainViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache
) : ViewModel() {

    private val waterProgressLiveData = MutableLiveData<Int>()
    val waterProgress: LiveData<Int> = waterProgressLiveData

    fun updateWaterProgress(seekBarLabelText: CharSequence, seekBarMaxValue: Int) {
        val currentSeekBarValue = (seekBarLabelText.toString().replace("ml", "").toFloat()) / 100
        val formattedResult = (currentSeekBarValue / seekBarMaxValue) * 100
        waterProgressLiveData.postValue(formattedResult.toInt())
    }

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()
}