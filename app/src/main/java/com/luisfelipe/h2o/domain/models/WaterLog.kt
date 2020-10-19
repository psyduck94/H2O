package com.luisfelipe.h2o.domain.models

import java.text.SimpleDateFormat
import java.util.*

data class WaterLog(
    val goalOfTheDay: Int = 20,
    val progress: Int = 0,
    val date: String,
) {
    fun progressInMl() = "${progress*100}/${goalOfTheDay*100}ml"

    fun progressInPercentage()
            = ((progress.toFloat()/goalOfTheDay.toFloat())*100).toInt()

    fun dayOfTheWeek(): String {
        val fullDate = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).parse(date)
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(fullDate)
    }
}


