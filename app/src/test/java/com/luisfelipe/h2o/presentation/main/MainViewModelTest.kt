package com.luisfelipe.h2o.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.luisfelipe.h2o.domain.enums.WaterAction
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.usecase.*
import com.luisfelipe.h2o.util.CoroutineTestRule
import com.luisfelipe.h2o.util.getOrAwaitValueTest
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var waterLog: WaterLog
    private lateinit var waterLog2: WaterLog

    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache = mockk()
    private val getWaterLogFromLocalDb: GetWaterLogFromLocalDb = mockk()
    private val saveWaterLogToLocalDb: SaveWaterLogToLocalDb = mockk()
    private val updateWaterLogFromLocalDb: UpdateWaterFromLocalDb = mockk()
    private val getTimeIntervalFromCache: GetTimeIntervalFromCache = mockk()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = spyk(
        MainViewModel(
            getGoalOfTheDayFromCache,
            getWaterLogFromLocalDb,
            saveWaterLogToLocalDb,
            updateWaterLogFromLocalDb,
            getTimeIntervalFromCache
        )
    )

    @Before
    fun setUp() {
        waterLog = WaterLog(
            progress = 10,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
        waterLog2 = WaterLog(
            progress = 5,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
    }

    @Test
    fun fetchWaterLogFromLocalDb_returnsWaterLog() {

        // Arrange
        coEvery { getWaterLogFromLocalDb() } returns waterLog

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.fetchWaterLogFromLocalDb()
        }

        // Assert
        val value = viewModel.waterLog.observedValue()
        assert(value != null)
        assert(value == waterLog)

    }

    
    @Test
    fun checkIfCanRemoveWater_waterGiven_returnsCorrectResult() {
        coEvery { getWaterLogFromLocalDb() } returns waterLog

        coroutinesTestRule.testDispatcher.runBlockingTest {
            val result = viewModel.checkIfCanRemoveWater(9)
            assertEquals(result, true)
        }
    }

    @Test
    fun createEmptyWaterLog_returnsEmptyWaterLog() {
        coEvery { getGoalOfTheDayFromCache() } returns 20
        coEvery { saveWaterLogToLocalDb(waterLog) } just Runs

        coroutinesTestRule.testDispatcher.runBlockingTest {
            val result = viewModel.createEmptyWaterLog()
            assertEquals(result, WaterLog(
                progress = 0,
                date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
            ))
        }

    }

}

fun <T> LiveData<T>.observedValue(): T? {
    observeForever {}
    return value
}