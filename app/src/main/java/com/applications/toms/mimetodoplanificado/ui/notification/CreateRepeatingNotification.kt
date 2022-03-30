package com.applications.toms.mimetodoplanificado.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.os.bundleOf
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.ui.notification.NotificationBundle.*
import com.applications.toms.mimetodoplanificado.ui.notification.RequestNotificationCode.DAILY_NOTIFICATION_CODE
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_21_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun createRepeatingNotification(context: Context, timeInMillis: Long, method: Method, totalDaysCycle: Int) {

    createNotificationChannel(context)

    val intent = Intent(context, NotificationReceiver::class.java)
    val bundle = bundleOf(
        NOTIFICATION_METHOD_KEY.key to method.name,
        NOTIFICATION_TIME_KEY.key to timeInMillis,
        NOTIFICATION_PERIODICITY_KEY.key to when (method) {
            Method.PILLS -> (24 * 60 * 60 * 1000)
            Method.RING -> (60 * 60 * 1000 * totalDaysCycle)
            Method.SHOOT -> (60 * 60 * 1000 * totalDaysCycle)
            Method.PATCH -> (60 * 60 * 1000 * totalDaysCycle)
        }
    )
    intent.putExtras(bundle)

    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            DAILY_NOTIFICATION_CODE.code,
            intent,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE
            else PendingIntent.FLAG_NO_CREATE
        )

    val myAlarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    //myAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    //myAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    myAlarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(timeInMillis, pendingIntent), pendingIntent)

}
