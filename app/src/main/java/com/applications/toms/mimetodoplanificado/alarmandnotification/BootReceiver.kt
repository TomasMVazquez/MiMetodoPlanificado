package com.applications.toms.mimetodoplanificado.alarmandnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createNotificationToShow
import com.applications.toms.mimetodoplanificado.ui.utils.onRebooted
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            onRebooted(context = context)

            createNotificationToShow(
                context = context,
                title = context.getString(R.string.notification_title_reboot),
                text = context.getString(R.string.notification_desc_reboot)
            )
        }
    }

}
