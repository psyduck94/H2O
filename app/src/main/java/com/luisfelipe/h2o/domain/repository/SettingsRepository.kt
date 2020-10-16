package com.luisfelipe.h2o.domain.repository

interface SettingsRepository {
    fun getGoalOfTheDay(): Int
    fun setGoalOfTheDay(goal: Int)
}