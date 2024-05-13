package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object EnqueueDailyNotificationWorker {
    fun enqueue(context: Context)  {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DATE, 1)
        }

        val timeDiff = calendar.timeInMillis - System.currentTimeMillis()

        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<DailyNotificationWorker>(24, TimeUnit.HOURS)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyNotification",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest,
        )
    }
}
