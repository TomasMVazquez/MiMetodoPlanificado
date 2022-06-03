package com.applications.toms.mimetodoplanificado.ui.screen.settings

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.createRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createCycleNotifications
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogSuccess
import com.applications.toms.mimetodoplanificado.ui.utils.convertToTimeInMills
import com.applications.toms.mimetodoplanificado.ui.utils.onRebooted
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun ConfirmRebootSettings(
    open: Boolean,
    context: Context,
    methodChosen: MethodChosen
) {
    onRebooted(context, false)
    var openConfirmDialog by remember { mutableStateOf(open) }

    AlertDialogSuccess(
        show = openConfirmDialog,
        successTitle = stringResource(id = R.string.success_dialog_title),
        successDescription = stringResource(id = R.string.success_reboot_dialog_desc),
    ) {
        methodChosen.methodAndStartDate.methodChosen?.let {
            val daysCycle = methodChosen.totalDaysCycle.toInt()
            val fromStart = methodChosen.methodAndStartDate.startDate.until(
                LocalDate.now(),
                ChronoUnit.DAYS
            )

            if (methodChosen.methodAndStartDate.methodChosen == Method.CYCLE) {
                createCycleNotifications(
                    context = context,
                    daysFromStart = fromStart
                )
            }

            if (methodChosen.isNotificationEnable)
                methodChosen.notificationTime?.let { notificationTime ->
                    createRepeatingNotification(
                        context = context,
                        timeInMillis = notificationTime.convertToTimeInMills(),
                        method = it,
                        totalDaysCycle = daysCycle,
                        daysFromStart = fromStart
                    )
                }
            else
                cancelRepeatingNotification(context)

            if (methodChosen.isAlarmEnable)
                methodChosen.alarmTime?.let { alarmTime ->
                    createRepeatingAlarm(
                        context = context,
                        timeInMillis = alarmTime.convertToTimeInMills(),
                        method = it,
                        totalDaysCycle = daysCycle,
                        daysFromStart = fromStart
                    )
                }
            else
                cancelRepeatingAlarm(context)
        }
        openConfirmDialog = false
    }
}
