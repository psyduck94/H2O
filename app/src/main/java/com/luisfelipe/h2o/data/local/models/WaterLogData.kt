package com.luisfelipe.h2o.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisfelipe.h2o.utils.GlobalConstants.Companion.DEFAULT_GOAL_OF_THE_DAY_VALUE

@Entity(tableName = "water_log")
data class WaterLogData(
    @PrimaryKey
    val date: String,
    val waterNeed: Int = DEFAULT_GOAL_OF_THE_DAY_VALUE,
    val waterDrunk: Int,
)