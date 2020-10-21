package com.luisfelipe.h2o.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisfelipe.h2o.domain.enums.InputState
import com.luisfelipe.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.luisfelipe.h2o.domain.usecase.SetGoalOfTheDayToCache
import com.luisfelipe.h2o.domain.usecase.UpdateGoalOfTheDayFromLocalDb
import com.luisfelipe.h2o.presentation.main.observedValue
import com.luisfelipe.h2o.util.CoroutineTestRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private val getGoalOfTheDayFromCache: GetGoalOfTheDayFromCache = mockk()
    private val setGoalOfTheDayToCache: SetGoalOfTheDayToCache = mockk()
    private val updateGoalOfTheDayFromLocalDb: UpdateGoalOfTheDayFromLocalDb = mockk()

    private val viewModel = spyk(
        SettingsViewModel(
            getGoalOfTheDayFromCache,
            setGoalOfTheDayToCache,
            updateGoalOfTheDayFromLocalDb
        )
    )

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getGoalOfTheDayFromCache_returnsGoalOfTheDay() {
        coEvery { getGoalOfTheDayFromCache() } returns 10

        viewModel.fetchGoalOfTheDayFromCache()

        coVerify(exactly = 1) { getGoalOfTheDayFromCache() }
    }

    @Test
    fun saveGoalOfTheDayToCache_emptyGoalGiven_returnsEmptyInputState() {
        val emptyGoal = ""

        viewModel.saveGoalOfTheDayToCache(emptyGoal)

        assert(viewModel.goalOfTheDayInputState.observedValue() == InputState.EMPTY)
    }

    @Test
    fun saveGoalOfTheDayToCache_invalidGoalGiven_returnsInvalidInputState() {
        val invalidGoal = "9999999"

        viewModel.saveGoalOfTheDayToCache(invalidGoal)

        assert(viewModel.goalOfTheDayInputState.observedValue() == InputState.INVALID)
    }

    @Test
    fun saveGoalOfTheDayToCache_validGoalGiven_returnsValidState() {
        val validGoal = "3000"

        coEvery { setGoalOfTheDayToCache(any()) } just Runs
        coEvery { updateGoalOfTheDayFromLocalDb(any()) } just Runs

        viewModel.saveGoalOfTheDayToCache(validGoal)

        assert(viewModel.goalOfTheDayInputState.observedValue() == InputState.VALID)

    }

    @Test
    fun saveGoalOfTheDayToCache_negativeGoalGiven_returnsInvalidState() {
        val invalidGoal = "-100"

        viewModel.saveGoalOfTheDayToCache(invalidGoal)

        assert(viewModel.goalOfTheDayInputState.observedValue() == InputState.INVALID)
    }

    @Test
    fun updateGoalOfTheDayFromLocalDatabase_validGoalGiven_updatesGoalOfTheDay() {

        val validGoal = "3000"
        coEvery { updateGoalOfTheDayFromLocalDb(any()) } just Runs

        viewModel.updateGoalOfTheDayFromLocalDatabase(validGoal)

        coVerify(exactly = 1) { updateGoalOfTheDayFromLocalDb(any()) }
    }
}