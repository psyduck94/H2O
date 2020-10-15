package com.example.h2o.data.local.mapper

import com.example.h2o.data.local.models.WaterLogData
import com.example.h2o.domain.models.WaterLog

internal class WaterLogMapper {
    companion object {

        fun mapLocalToDomain(waterLogData: WaterLogData?): WaterLog? {
            return if (waterLogData != null) {
                WaterLog(
                    progress = waterLogData.waterDrunk,
                    goalOfTheDay = waterLogData.waterNeed,
                    date = waterLogData.currentDate
                )
            } else null
        }

        fun mapLocalToDomain(waterLogDataList: List<WaterLogData>): List<WaterLog> {
            val dateLogList = mutableListOf<WaterLog>()
            for (dateLogData in waterLogDataList) {
                val dateLog = WaterLog(
                    progress = dateLogData.waterDrunk,
                    goalOfTheDay = dateLogData.waterNeed,
                    date = dateLogData.currentDate
                )
                dateLogList.add(dateLog)
            }
            return dateLogList
        }

        fun mapDomainToLocal(waterLog: WaterLog): WaterLogData {
            return WaterLogData(
                waterDrunk = waterLog.progress,
                waterNeed = waterLog.goalOfTheDay,
                currentDate = waterLog.date
            )
        }
    }
}