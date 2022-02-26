package com.applications.toms.domain

data class MethodChosen(
    val methodAndStartDate: MethodAndStartDate,
    val pillsBreakDays: Int,
    val notifications: Boolean,
    val notificationTime: String,
    val alarm: Boolean,
    val alarmTime: String
)
