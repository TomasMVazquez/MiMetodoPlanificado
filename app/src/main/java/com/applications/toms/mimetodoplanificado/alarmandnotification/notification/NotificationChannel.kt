package com.applications.toms.mimetodoplanificado.alarmandnotification.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.applications.toms.mimetodoplanificado.R

const val NOTIF_CHANNEL_ID = "notification_channel_id"
const val CYCLE_NOTIF_CHANNEL_ID = "cycle_notification_channel_id"

fun createNotificationChannel(context: Context, isCycle: Boolean = false) {
    val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.getNotificationChannel(if (isCycle) CYCLE_NOTIF_CHANNEL_ID else NOTIF_CHANNEL_ID) != null) return

    val name = if (isCycle) context.getString(R.string.cycle_notification_channel_name)
    else context.getString(R.string.notification_channel_name)

    val descriptionText = if (isCycle) context.getString(R.string.cycle_notification_channel_description)
    else context.getString(R.string.notification_channel_description)

    val importance = if (isCycle) NotificationManager.IMPORTANCE_DEFAULT else NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(if (isCycle) CYCLE_NOTIF_CHANNEL_ID else NOTIF_CHANNEL_ID, name, importance).apply {
        description = descriptionText
    }

    notificationManager.createNotificationChannel(channel)
}