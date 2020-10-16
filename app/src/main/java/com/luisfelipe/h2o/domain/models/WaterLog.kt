package com.luisfelipe.h2o.domain.models

import java.text.SimpleDateFormat
import java.util.*

data class WaterLog(
    val goalOfTheDay: Int = 20,
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
        val fullDate = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).parse(date)
        dayOfTheWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(fullDate)
    }
}


