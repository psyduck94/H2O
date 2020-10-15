package com.example.h2o.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.h2o.data.local.dao.WaterLogDao
import com.example.h2o.data.local.models.WaterLogData

@Database(version = 1, entities = [WaterLogData::class])
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun dateLogDao(): WaterLogDao
}