package com.example.h2o.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.h2o.utils.GlobalConstants.Companion.DEFAULT_GOAL_OF_THE_DAY_VALUE
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "water_log")
data class WaterLogData(
    @PrimaryKey
    val date: String,
    val waterNeed: Int = DEFAULT_GOAL_OF_THE_DAY_VALUE,
    val waterDrunk: Int,
)