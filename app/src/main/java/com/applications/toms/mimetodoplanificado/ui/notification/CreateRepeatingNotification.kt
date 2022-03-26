package com.applications.toms.mimetodoplanificado.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.ui.notification.NotificationBundle.NOTIFICATION_BUNDLE_KEY
import com.applications.toms.mimetodoplanificado.ui.notification.RequestNotificationCode.DAILY_NOTIFICATION_CODE
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun createRepeatingNotification(context: Context, timeInMillis: Long, method: Method) {

    val intent = Intent(context, NotificationReceiver::class.java)
    intent.putExtra(NOTIFICATION_BUNDLE_KEY.key, method.name)
    val pendingIntent =
        PendingIntent.getBroadcast(context, DAILY_NOTIFICATION_CODE.code, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val myAlarmManager: AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    myAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
}
