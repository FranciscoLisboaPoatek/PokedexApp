package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pokedexapp.ui.utils.DAILY_NOTIFICATION_WORKER_ID_KEY
import java.util.Calendar
import java.util.concurrent.TimeUnit

object EnqueueDailyNotificationWorker {
    fun enqueue(context: Context) {
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
            DAILY_NOTIFICATION_WORKER_ID_KEY,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest,
        )
    }
}
