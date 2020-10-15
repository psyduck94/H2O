package com.example.h2o.domain.models

class WaterManager(
    private val goalOfTheDay: Int,
    private val waterProgress: Int = 0) {

    fun goalOfTheDayInMl() = goalOfTheDay*100

    fun goalOfTheDayValue() =  goalOfTheDay.toFloat()

    fun waterProgressInMl() = waterProgress*100

    fun waterProgressValue() = waterProgress.toFloat()

    fun waterProgressInPercentage() = (waterProgressInMl()/goalOfTheDayInMl())*100

}