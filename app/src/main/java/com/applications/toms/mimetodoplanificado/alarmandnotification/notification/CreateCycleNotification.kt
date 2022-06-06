package com.applications.toms.mimetodoplanificado.alarmandnotification.notification

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
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.RequestNotificationCode.CYCLE_NOTIFICATION_CODE
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_DAY_21
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_DAY_25
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_DAY_29
import com.google.accompanist.pager.ExperimentalPagerApi
import java.util.Calendar
import java.util.concurrent.TimeUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun createCycleNotifications(
    context: Context,
    daysFromStart: Long = 0
) {

    createNotificationChannel(context, true)

    when (daysFromStart) {
        in Long.MIN_VALUE..21 -> {
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_21)
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_25)
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_29)
        }
        in 22..25 -> {
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_25)
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_29)
        }
        in 29..Long.MAX_VALUE -> {
            createNotificationAtDay(context, daysFromStart, CYCLE_DAY_29)
        }
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun createNotificationAtDay(context: Context, daysFromStart: Long, atDay: Long) {
    val myAlarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    val cal = Calendar.getInstance()
    cal.apply {
        set(Calendar.HOUR_OF_DAY, 9)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        add(Calendar.DATE, (atDay - daysFromStart).toInt())
    }

    val intent = Intent(context, NotificationReceiver::class.java)
    val bundle = bundleOf(
        NOTIFICATION_METHOD_KEY.key to Method.CYCLE.name,
        NOTIFICATION_CYCLE_KEY.key to atDay.toInt()
    )
    intent.putExtras(bundle)

    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            CYCLE_NOTIFICATION_CODE.code.plus(atDay).toInt(),
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

    cancelCycleNotification(myAlarmManager, pendingIntent)

    myAlarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun cancelCycleNotification(
    myAlarmManager: AlarmManager,
    pendingIntent: PendingIntent
) {
    myAlarmManager.cancel(pendingIntent)
}
