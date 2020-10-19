package com.luisfelipe.h2o.domain.models

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class WaterLogTest {

    private lateinit var waterLog: WaterLog

    @Before
    fun setUp() {
        waterLog = WaterLog(
            progress = 10,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
    }

    @Test
    fun calculateProgressInMl_progressGiven_returnsCorrectResult() {
        val result = waterLog.progressInMl()
        assertEquals(result, "1000/2000ml")
    }

    @Test
    fun calculateProgressInPercentage_progressGiven_returnsCorrectResult() {
        val result = waterLog.progressInPercentage()
        assertEquals(result, 50)
    }

    @Test
    fun calculateDayOfTheWeek_dateGiven_returnsCorrectResult() {
        val result = waterLog.dayOfTheWeek()
        assertEquals(result, "Monday")
    }
}