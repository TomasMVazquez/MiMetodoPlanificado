package com.applications.toms.domain

data class MethodChosen(
    val methodAndStartDate: MethodAndStartDate,
    val totalDaysCycle: Long,
    val breakDays: Int,
    val isNotificationEnable: Boolean,
    val notificationTime: String?,
    val isAlarmEnable: Boolean,
    val alarmTime: String?
)
