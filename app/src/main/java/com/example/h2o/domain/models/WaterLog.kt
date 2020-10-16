package com.example.h2o.domain.models

import com.example.h2o.utils.GlobalConstants.Companion.DEFAULT_GOAL_OF_THE_DAY_VALUE
import java.text.SimpleDateFormat
import java.util.*

data class WaterLog(
    val goalOfTheDay: Int = DEFAULT_GOAL_OF_THE_DAY_VALUE,
    var progress: Int = 0,
    var progressInMl: String = "0/${goalOfTheDay*100}",
    var progressInPercentage: Int = 0,
    val date: String,
    var dayOfTheWeek: String = ""
) {
    init {
        formatProgressInMl()
        formatProgressInPercentage()
        formatDayOfTheWeek()
    }

    private fun formatProgressInMl() {
        progressInMl = "${progress*100}/${goalOfTheDay*100}ml"
    }

    private fun formatProgressInPercentage() {
        val result = (progress.toFloat()/goalOfTheDay.toFloat())*100
        progressInPercentage = result.toInt()
    }

    private fun formatDayOfTheWeek() {
        dayOfTheWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
    }
}


