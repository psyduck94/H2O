package com.luisfelipe.h2o.services

import android.content.Context
import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DrinkWaterWorkerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val config = Configuration.Builder()
            // Set log level to Log.DEBUG to make it easier to debug
            .setMinimumLoggingLevel(Log.DEBUG)
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testPeriodicWorker() {
        val request = PeriodicWorkRequestBuilder<DrinkWaterWorker>(3, TimeUnit.HOURS)
            .setInitialDelay(3, TimeUnit.HOURS)
            .build()

        val workManager = WorkManager.getInstance(context)


        workManager.enqueueUniquePeriodicWork(
            "NotificationWorkTag",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        ).result.get()

        val workInfo = workManager.getWorkInfoById(request.id).get()

        WorkManagerTestInitHelper.getTestDriver(context)?.setPeriodDelayMet(request.id)

        assertThat(workInfo.state, `is`(WorkInfo.State.ENQUEUED))
    }
}