package com.applications.toms.mimetodoplanificado.ui.notification

enum class RequestNotificationCode(val code: Int) {
    DAILY_NOTIFICATION_CODE(100)
}

enum class NotificationBundle(val key: String) {
    NOTIFICATION_METHOD_KEY("method"),
    NOTIFICATION_PERIODICITY_KEY("periodicity"),
    NOTIFICATION_TIME_KEY("time")
}