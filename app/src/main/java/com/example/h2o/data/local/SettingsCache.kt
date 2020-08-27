package com.example.h2o.data.local

import android.content.SharedPreferences

class SettingsCache(private val sharedPreferences: SharedPreferences) {

    private companion object {

        const val WATER_GOAL_OF_THE_DAY_KEY = "WATER_GOAL"
        const val DEFAULT_WATER_GOAL_OF_THE_DAY = 20

        const val TIME_REMINDER_KEY = "TIME_REMINDER"
    }

    fun getWaterGoalOfTheDay() =
         sharedPreferences.getInt(WATER_GOAL_OF_THE_DAY_KEY, DEFAULT_WATER_GOAL_OF_THE_DAY)

    fun setWaterGoalOfTheDay(waterGoalOfTheDay: Int) =
        sharedPreferences.edit().putInt(WATER_GOAL_OF_THE_DAY_KEY, waterGoalOfTheDay).apply()

}