package com.applications.toms.mimetodoplanificado.ui.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.applications.toms.mimetodoplanificado.MainActivity
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
fun createNotificationToShow(context: Context) {
    val notificationId = 1
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = PendingIntent.getActivity(context,notificationId,intent,PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_icono)
        .setColor(Purple.toArgb()) //TODO CHANGE COLOR IF WE ARE ON DARK THEME?
        .setContentTitle("TITULO")
        .setContentText("Texto descriptivo")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}