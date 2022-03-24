package com.applications.toms.domain

data class MethodChosen(
    val methodAndStartDate: MethodAndStartDate,
    val totalDaysCycle: Long,
    val breakDays: Int,
    val notifications: Boolean,
    val notificationTime: String,
    val alarm: Boolean,
    val alarmTime: String
)
