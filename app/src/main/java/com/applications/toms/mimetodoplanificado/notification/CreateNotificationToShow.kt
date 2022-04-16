package com.applications.toms.mimetodoplanificado.notification

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
import com.applications.toms.mimetodoplanificado.notification.RequestNotificationCode.DAILY_NOTIFICATION_CODE
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
fun createNotificationToShow(
    context: Context,
    title: String,
    text: String
) {

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        action = context.packageName + "." + DAILY_NOTIFICATION_CODE.code
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        DAILY_NOTIFICATION_CODE.code,
        intent,
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
        else PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(context, NOTIF_CHANNEL_ID)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_icono)
        .setColor(Purple.toArgb())
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(DAILY_NOTIFICATION_CODE.code, builder.build())
    }
}