package com.example.h2o.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.h2o.data.local.repositories.WaterLogRepositoryImpl
import com.example.h2o.data.local.repositories.SettingsRepositoryImpl
import com.example.h2o.data.local.database.AppDatabase
import com.example.h2o.data.local.cache.SettingsCache
import com.example.h2o.domain.repository.WaterLogRepository
import com.example.h2o.domain.repository.SettingsRepository
import com.example.h2o.domain.usecase.*
import com.example.h2o.presentation.archives.ArchivesViewModel
import com.example.h2o.presentation.archives.WaterLogAdapter
import com.example.h2o.presentation.main.MainViewModel
import com.example.h2o.presentation.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val APP_DATABASE = "APP_DATABASE"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val appModule = module {

    viewModel {
        MainViewModel(get(), get(), get(), get())
    }

    viewModel {
        ArchivesViewModel(get())
    }

    viewModel {
        SettingsViewModel(get(), get(), get())
    }

    factory { WaterLogAdapter() }

    factory { UpdateGoalOfTheDayFromLocalDb(get()) }

    factory { GetTheLast7WaterLogsFromLocalDb(get()) }

    factory { UpdateWaterLocalFromLocalDb(get()) }

    factory { GetWaterLogFromLocalDb(get()) }

    factory { SaveWaterLogToLocalDb(get()) }

    factory { GetGoalOfTheDayFromCache(get()) }

    factory { SetGoalOfTheDayToCache(get()) }


    factory {
        WaterLogRepositoryImpl(
            get()
        ) as WaterLogRepository
    }

    factory {
        SettingsRepositoryImpl(
            get()
        ) as SettingsRepository
    }

    factory { SettingsCache(getSharedPreferences(androidContext())) }

    single(named(APP_DATABASE)) {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            APP_DATABASE
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<AppDatabase>(named(APP_DATABASE)).dateLogDao()
    }

}

private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)