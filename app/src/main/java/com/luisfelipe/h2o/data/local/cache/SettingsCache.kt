package com.luisfelipe.h2o.data.local.cache

import android.content.SharedPreferences

class SettingsCache(private val sharedPreferences: SharedPreferences) {

    private companion object {
        const val WATER_GOAL_OF_THE_DAY_KEY = "WATER_GOAL"
        const val DEFAULT_WATER_GOAL_OF_THE_DAY = 20

        const val TIME_REMINDER_STATE_KEY = "NOTIFICATION_SWITCH"
        const val DEFAULT_TIME_REMINDER_STATE = "OFF"

        const val TIME_INTERVAL = "EVERY_X_HOURS"
        const val DEFAULT_TIME_INTERVAL = 3
    }

    fun getWaterGoalOfTheDay() =
         sharedPreferences.getInt(WATER_GOAL_OF_THE_DAY_KEY, DEFAULT_WATER_GOAL_OF_THE_DAY)

    fun setWaterGoalOfTheDay(waterGoalOfTheDay: Int) =
        sharedPreferences.edit().putInt(WATER_GOAL_OF_THE_DAY_KEY, waterGoalOfTheDay).apply()

    fun getTimeReminderState() =
        sharedPreferences.getString(TIME_REMINDER_STATE_KEY, DEFAULT_TIME_REMINDER_STATE)

    fun setTimeReminderState(timeReminderState: String) =
        sharedPreferences.edit().putString(TIME_REMINDER_STATE_KEY, timeReminderState)

    fun getTimeInterval() =
        sharedPreferences.getLong(TIME_INTERVAL, DEFAULT_TIME_INTERVAL.toLong())

    fun setTimeInterval(hours: Long) =
        sharedPreferences.edit().putLong(TIME_INTERVAL, hours).apply()
}