package com.example.h2o.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.h2o.domain.usecase.GetCurrentWaterValueFromCache
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.example.h2o.domain.usecase.SetCurrentWaterValueToCache

class MainViewModel(
    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache,
    private val getCurrentWaterValueFromCache: GetCurrentWaterValueFromCache,
    private val setCurrentWaterValueToCache: SetCurrentWaterValueToCache
) : ViewModel() {

    private val waterProgressLiveData = MutableLiveData<Int>()
    val waterProgress: LiveData<Int> = waterProgressLiveData

    private val reachedMaxWaterProgressLiveData = MutableLiveData<Unit>()
    val reachedMaxWaterProgress: LiveData<Unit> = reachedMaxWaterProgressLiveData

    fun updateWaterProgress(seekBarLabelText: CharSequence, seekBarMaxValue: Int) {
        val currentSeekBarValue = (seekBarLabelText.toString().replace("ml", "").toFloat()) / 100
        val formattedResult = (currentSeekBarValue / seekBarMaxValue) * 100

        setCurrentWaterValueToCache(currentSeekBarValue.toInt())
        waterProgressLiveData.postValue(formattedResult.toInt())
    }

    fun fetchGoalOfTheDayFromCache() = getGoalOfTheDayFromCache()

    fun fetchCurrentWaterValueFromCache(): Int {
        val goalOfTheDay = getGoalOfTheDayFromCache().toFloat()
        val currentValue = getCurrentWaterValueFromCache().toFloat()
        return ((currentValue/goalOfTheDay)*100).toInt()
    }

    fun getCurrentWaterInMl(): String {
        val goalOfTheDay = getGoalOfTheDayFromCache() * 100
        val currentValue = getCurrentWaterValueFromCache() * 100
        return "$currentValue/${goalOfTheDay}ml"
    }

}