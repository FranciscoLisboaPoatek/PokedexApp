package com.example.pokedexapp.workers

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.pokedexapp.ui.notifications.EnqueueDailyNotificationWorker
import com.example.pokedexapp.ui.utils.DAILY_NOTIFICATION_WORKER_ID_KEY
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class DailyNotificationWorkerScheduleTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun before_midday() {
        val currentTime = Calendar.getInstance()
        currentTime.set(0, 0, 0, 11, 0, 0)

        val expectedTime = Calendar.getInstance()
        expectedTime.set(0, 0, 0, 12, 0, 0)

        val workManager = WorkManager.getInstance(context)

        EnqueueDailyNotificationWorker().enqueue(context, currentTime)

        val workInfo = workManager.getWorkInfosForUniqueWork(DAILY_NOTIFICATION_WORKER_ID_KEY)
            .get()
            .firstOrNull()

        assertEquals(WorkInfo.State.ENQUEUED, workInfo?.state)

        val workSpec = workInfo?.run {
            workManager.getWorkInfoById(id).get()
        }

        assertEquals(expectedTime.timeInMillis - currentTime.timeInMillis, workSpec?.initialDelayMillis)

    }

    @Test
    fun after_midday() {
        val currentTime = Calendar.getInstance()
        currentTime.set(0, 0, 0, 13, 0, 0)

        val expectedTime = Calendar.getInstance()
        expectedTime.set(0, 0, 1, 12, 0, 0)

        val workManager = WorkManager.getInstance(context)

        EnqueueDailyNotificationWorker().enqueue(context, currentTime)

        val workInfo = workManager.getWorkInfosForUniqueWork(DAILY_NOTIFICATION_WORKER_ID_KEY)
            .get()
            .firstOrNull()

        assertEquals(WorkInfo.State.ENQUEUED, workInfo?.state)

        val workSpec = workInfo?.run {
            workManager.getWorkInfoById(id).get()
        }

        assertEquals(expectedTime.timeInMillis - currentTime.timeInMillis, workSpec?.initialDelayMillis)
    }
}