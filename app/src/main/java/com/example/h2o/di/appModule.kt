package com.example.h2o.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.h2o.data.SettingsRepositoryImpl
import com.example.h2o.data.local.SettingsCache
import com.example.h2o.domain.repository.SettingsRepository
import com.example.h2o.domain.usecase.GetCurrentWaterValueFromCache
import com.example.h2o.domain.usecase.GetGoalOfTheDayFromCache
import com.example.h2o.domain.usecase.SetCurrentWaterValueToCache
import com.example.h2o.domain.usecase.SetGoalOfTheDayToCache
import com.example.h2o.presentation.main.MainViewModel
import com.example.h2o.presentation.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val appModule = module {

    viewModel {
        MainViewModel(get(), get(), get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

    factory { GetGoalOfTheDayFromCache(get()) }

    factory { SetGoalOfTheDayToCache(get()) }

    factory { GetCurrentWaterValueFromCache(get()) }

    factory { SetCurrentWaterValueToCache(get()) }

    factory {
        SettingsRepositoryImpl(
            get()
        ) as SettingsRepository
    }

    factory { SettingsCache(getSharedPreferences(androidContext())) }

}

private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)