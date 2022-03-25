package com.applications.toms.mimetodoplanificado.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.applications.toms.mimetodoplanificado.R

const val CHANNEL_ID = "notification_channel_id"

fun createNotificationChannel(context: Context) {
    val name = context.getString(R.string.notification_channel_name)
    val descriptionText = context.getString(R.string.notification_channel_description)
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}