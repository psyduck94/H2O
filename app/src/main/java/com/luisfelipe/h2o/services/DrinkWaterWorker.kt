package com.luisfelipe.h2o.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.luisfelipe.h2o.R
import com.luisfelipe.h2o.presentation.main.MainActivity
import com.luisfelipe.h2o.utils.GlobalConstants

class DrinkWaterWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            showNotification()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification() {
        val notificationId = 1
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val pendingIntent = createPendingIntent()

        val notificationBuilder =
            NotificationCompat.Builder(context, GlobalConstants.WATER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_water_drop)
                .setSound(defaultSoundUri)
                .setContentTitle(context.getString(R.string.water_notification_title))
                .setContentText(context.getString(R.string.water_notification_content))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        val channel = createNotificationChannel()
        channel?.notify(notificationId, notificationBuilder.build())
    }

    private fun createPendingIntent(): PendingIntent {
        val tapResultIntent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationChannel(): NotificationManager? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_water_reminder)
            val descriptionText = context.getString(R.string.channel_water_description)

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel =
                NotificationChannel(GlobalConstants.WATER_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager
        } else null
    }

}