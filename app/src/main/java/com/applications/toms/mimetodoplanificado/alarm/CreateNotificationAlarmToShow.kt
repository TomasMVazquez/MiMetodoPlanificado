package com.applications.toms.mimetodoplanificado.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
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
fun createNotificationAlarmToShow(
    context: Context,
    title: String,
    text: String
) {

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        DAILY_NOTIFICATION_CODE.code,
        intent,
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE
        else PendingIntent.FLAG_ONE_SHOT
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_icono)
        .setColor(Purple.toArgb()) //TODO CHANGE COLOR IF WE ARE ON DARK THEME?
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(DAILY_NOTIFICATION_CODE.code, builder.build())
    }
}