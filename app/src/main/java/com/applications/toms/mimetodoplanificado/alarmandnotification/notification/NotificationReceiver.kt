package com.applications.toms.mimetodoplanificado.alarmandnotification.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.NotificationBundle.NOTIFICATION_CYCLE_KEY
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.NotificationBundle.NOTIFICATION_METHOD_KEY
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_21_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_7_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.google.accompanist.pager.ExperimentalPagerApi
import java.util.concurrent.TimeUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras

        var title = context.getString(R.string.app_name)
        var text = context.getString(R.string.notification_desc_generic)

        var delay: Long? = null
        var method: Method? = null
        val currentTime: Long = System.currentTimeMillis()
        var daysOfDelay = 1L

        when (bundle?.get(NOTIFICATION_METHOD_KEY.key)) {
            Method.PILLS.name -> {
                title = context.getString(R.string.pills)
                text = context.getString(R.string.notification_desc_pills)
                method = Method.PILLS
                delay = currentTime + TimeUnit.HOURS.toMillis(24L)
            }
            Method.RING.name -> {
                title = context.getString(R.string.ring)
                text = if (bundle.getInt(NOTIFICATION_CYCLE_KEY.key) == 21)
                    context.getString(R.string.notification_desc_out_ring)
                else context.getString(R.string.notification_desc_in_ring)
                method = Method.RING
                daysOfDelay =
                    if (bundle.getInt(NOTIFICATION_CYCLE_KEY.key) == 21) CYCLE_7_DAYS else CYCLE_21_DAYS
                delay = currentTime + TimeUnit.DAYS.toMillis(daysOfDelay)
            }
            Method.PATCH.name -> {
                title = context.getString(R.string.patch)
                text = if (bundle.getInt(NOTIFICATION_CYCLE_KEY.key) == 21)
                    context.getString(R.string.notification_desc_out_path)
                else context.getString(R.string.notification_desc_in_path)
                method = Method.PATCH
                daysOfDelay =
                    if (bundle.getInt(NOTIFICATION_CYCLE_KEY.key) == 21) CYCLE_7_DAYS else CYCLE_21_DAYS
                delay = currentTime + TimeUnit.HOURS.toMillis(daysOfDelay)
            }
            Method.SHOOT.name -> {
                title = context.getString(R.string.injection)
                text = context.getString(R.string.notification_desc_shoot)
                method = Method.SHOOT
                daysOfDelay = bundle.getInt(NOTIFICATION_CYCLE_KEY.key).toLong()
                delay = currentTime + TimeUnit.HOURS.toMillis(daysOfDelay)
            }
            else -> {}
        }

        safeLet(method, delay) { m, d ->
            createRepeatingNotification(
                context = context,
                timeInMillis = d,
                method = m,
                totalDaysCycle = daysOfDelay.toInt()
            )
        }

        createNotificationToShow(context, title, text)
    }

}
