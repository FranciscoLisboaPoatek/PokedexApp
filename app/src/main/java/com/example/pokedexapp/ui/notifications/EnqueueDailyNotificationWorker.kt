package com.example.pokedexapp.ui.notifications

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pokedexapp.ui.utils.DAILY_NOTIFICATION_WORKER_ID_KEY
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EnqueueDailyNotificationWorker
    @Inject
    constructor() : EnqueueWorker {
        override fun enqueue(
            context: Context,
            calendar: Calendar,
        ) {
            val delayToSchedule = calculateDelayToSchedule(calendar)

            val constraints =
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

            val dailyWorkRequest =
                OneTimeWorkRequestBuilder<DailyNotificationWorker>()
                    .setInitialDelay(delayToSchedule, TimeUnit.MILLISECONDS)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                DAILY_NOTIFICATION_WORKER_ID_KEY,
                ExistingWorkPolicy.APPEND_OR_REPLACE,
                dailyWorkRequest,
            )
        }
    }

private fun calculateDelayToSchedule(
    currentCalendar: Calendar,
    hourOfTheDay: Int = 12,
): Long {
    val calendar = currentCalendar.clone() as Calendar

    calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    if (currentCalendar.after(calendar)) {
        calendar.add(Calendar.DATE, 1)
    }

    val timeDiff = calendar.timeInMillis - currentCalendar.timeInMillis

    return timeDiff
}
