package com.luisfelipe.h2o.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luisfelipe.h2o.domain.enums.WaterAction
import com.luisfelipe.h2o.domain.models.WaterLog
import com.luisfelipe.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.luisfelipe.h2o.domain.usecase.GetWaterLogFromLocalDb
import com.luisfelipe.h2o.domain.usecase.SaveWaterLogToLocalDb
import com.luisfelipe.h2o.domain.usecase.UpdateWaterFromLocalDb
import com.luisfelipe.h2o.util.CoroutineTestRule
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

    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache = mockk()
    private val getWaterLogFromLocalDb: GetWaterLogFromLocalDb = mockk()
    private val saveWaterLogToLocalDb: SaveWaterLogToLocalDb = mockk()
    private val updateWaterLogToLocalDb: UpdateWaterFromLocalDb = mockk()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = spyk(
        MainViewModel(
            getGoalOfTheDayFromCache,
            getWaterLogFromLocalDb,
            saveWaterLogToLocalDb,
            updateWaterLogToLocalDb
        )
    )

    @Before
    fun setUp() {
        waterLog = WaterLog(
            progress = 10,
            date = SimpleDateFormat("yyyy-M-dd", Locale.getDefault()).format(Date())
        )
    }

    @Test
    fun fetchWaterLogFromLocalDb_returnsWaterLog() {

        coEvery { getWaterLogFromLocalDb() } returns waterLog
        coEvery { viewModel.waterLogLiveData.postValue(waterLog) } just Runs

        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.fetchWaterLogFromLocalDb()
        }

        verify { viewModel.waterLogLiveData.postValue(waterLog)}

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