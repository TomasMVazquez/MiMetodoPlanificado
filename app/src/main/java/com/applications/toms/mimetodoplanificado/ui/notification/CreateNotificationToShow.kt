package com.applications.toms.mimetodoplanificado.ui.notification

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.theme.Purple

fun createNotificationToShow(context: Context) {
    val notificationId = 1
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_icono)
        .setColor(Purple.toArgb()) //TODO CHANGE COLOR IF WE ARE ON DARK THEME?
        .setContentTitle("TITULO")
        .setContentText("Texto descriptivo")
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}