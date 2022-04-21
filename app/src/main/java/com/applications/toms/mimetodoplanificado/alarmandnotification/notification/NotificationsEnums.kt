package com.applications.toms.mimetodoplanificado.alarmandnotification.notification

enum class RequestNotificationCode(val code: Int) {
    DAILY_NOTIFICATION_CODE(100),
    ALARM_CODE(200)
}

enum class NotificationBundle(val key: String) {
    NOTIFICATION_METHOD_KEY("method"),
    NOTIFICATION_CYCLE_KEY("cycle"),
}