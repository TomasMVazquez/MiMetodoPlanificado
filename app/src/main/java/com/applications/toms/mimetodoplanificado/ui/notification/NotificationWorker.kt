package com.applications.toms.mimetodoplanificado.ui.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class NotificationWorker(
    context: Context,
    userParameters: WorkerParameters
) : Worker(context, userParameters) {

    override fun doWork(): Result = try {
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }

}
