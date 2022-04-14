package com.applications.toms.mimetodoplanificado.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import com.applications.toms.mimetodoplanificado.R


const val ALARM_CHANNEL_ID = "alarm_channel_id"

fun createAlarmChannel(context: Context) {
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.getNotificationChannel(ALARM_CHANNEL_ID) != null) return

    val name = context.getString(R.string.alarm_channel_name)
    val descriptionText = context.getString(R.string.alarm_channel_description)
    val importance = NotificationManager.IMPORTANCE_HIGH
    val audioAttributes = AudioAttributes.Builder().apply {
        setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        setUsage(AudioAttributes.USAGE_NOTIFICATION)
    }.build()
    val vibratorPattern = longArrayOf(100, 500, 1000)
    val soundUri: Uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)

    val channel = NotificationChannel(ALARM_CHANNEL_ID, name, importance).apply {
        description = descriptionText
        enableLights(true)
        enableVibration(true)
        lightColor = Color.RED
        setSound(soundUri, audioAttributes)
        vibrationPattern = vibratorPattern
    }

    notificationManager.createNotificationChannel(channel)
}