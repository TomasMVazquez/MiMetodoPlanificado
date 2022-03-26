package com.applications.toms.mimetodoplanificado.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.applications.toms.mimetodoplanificado.R
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var title = context.getString(R.string.app_name)
        var text = context.getString(R.string.notification_desc_generic)

        createNotificationToShow(context, title, text)
    }

}
