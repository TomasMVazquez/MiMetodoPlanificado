package com.applications.toms.mimetodoplanificado.alarmandnotification.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.os.bundleOf
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.NotificationBundle.NOTIFICATION_CYCLE_KEY
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.NotificationBundle.NOTIFICATION_METHOD_KEY
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.RequestNotificationCode.DAILY_NOTIFICATION_CODE
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_21_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_7_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.google.accompanist.pager.ExperimentalPagerApi
import java.util.concurrent.TimeUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun createRepeatingAlarm(
    context: Context,
    timeInMillis: Long,
    method: Method,
    totalDaysCycle: Int,
    daysFromStart: Long = 0
) {

    var time = timeInMillis
    createAlarmChannel(context)

    val intent = Intent(context, AlarmReceiver::class.java)
    val bundle = bundleOf(
        NOTIFICATION_METHOD_KEY.key to method.name,
        NOTIFICATION_CYCLE_KEY.key to when (method) {
            Method.PILLS -> {
                if (System.currentTimeMillis() - timeInMillis > 0) time = timeInMillis + TimeUnit.HOURS.toMillis(24L)
                totalDaysCycle
            }
            Method.RING,
            Method.SHOOT -> {
                if (totalDaysCycle == 28) {
                    if (daysFromStart < CYCLE_21_DAYS) {
                        time = timeInMillis + TimeUnit.DAYS.toMillis(CYCLE_21_DAYS - daysFromStart)
                        CYCLE_21_DAYS
                    }else{
                        time = timeInMillis + TimeUnit.DAYS.toMillis(TOTAL_CYCLE_DAYS - daysFromStart)
                        CYCLE_7_DAYS
                    }
                } else totalDaysCycle
            }
            Method.PATCH -> {
                time = timeInMillis + TimeUnit.DAYS.toMillis(totalDaysCycle.toLong() - daysFromStart)
                totalDaysCycle
            }
        }
    )
    intent.putExtras(bundle)

    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            DAILY_NOTIFICATION_CODE.code,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

    val myAlarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    myAlarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(time, pendingIntent), pendingIntent)
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun cancelRepeatingAlarm(
    context: Context
) {
    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            DAILY_NOTIFICATION_CODE.code,
            Intent(context, AlarmReceiver::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

    val myAlarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    myAlarmManager.cancel(pendingIntent)
}