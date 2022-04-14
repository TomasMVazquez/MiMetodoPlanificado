package com.applications.toms.mimetodoplanificado.alarm

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
import com.applications.toms.mimetodoplanificado.notification.RequestNotificationCode.ALARM_CODE
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
fun createNotificationAlarmToShow(
    context: Context,
    title: String,
    text: String
) {

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        action = context.packageName + "." + ALARM_CODE.code
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        ALARM_CODE.code,
        intent,
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
        else PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_icono)
        .setColor(VividRaspberry.toArgb())
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(ALARM_CODE.code, builder.build())
    }
}