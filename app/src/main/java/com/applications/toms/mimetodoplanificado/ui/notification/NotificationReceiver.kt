package com.applications.toms.mimetodoplanificado.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.notification.NotificationBundle.*
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        var title = context.getString(R.string.app_name)
        var text = context.getString(R.string.notification_desc_generic)
        //TODO Add different titles and desc
        val periodicity: Int? = bundle?.getInt(NOTIFICATION_PERIODICITY_KEY.key)
        val time: Long? = bundle?.getLong(NOTIFICATION_TIME_KEY.key)

        when (bundle?.get(NOTIFICATION_METHOD_KEY.key)) {
            Method.PILLS.name -> {
                title = context.getString(R.string.pills)
                text = context.getString(R.string.notification_desc_pills)

                safeLet(time, periodicity) { x, y ->
                    createRepeatingNotification(
                        context = context,
                        timeInMillis = x + y,
                        method = Method.PILLS,
                        totalDaysCycle = 1
                    )
                }
            }
            Method.RING.name -> {
                title = context.getString(R.string.ring)
                text = context.getString(R.string.notification_desc_ring)
            }
            Method.PATCH.name -> {
                title = context.getString(R.string.patch)
                text = context.getString(R.string.notification_desc_path)
            }
            Method.SHOOT.name -> {
                title = context.getString(R.string.injection)
                text = context.getString(R.string.notification_desc_shoot)
            }
            else -> {}
        }

        createNotificationToShow(context, title, text)
    }

}
