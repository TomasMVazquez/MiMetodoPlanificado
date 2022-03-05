package com.applications.toms.mimetodoplanificado.data.mapper

import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod
import com.applications.toms.mimetodoplanificado.ui.screen.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString

fun MethodChosen.toDatabaseModel(): ChosenMethod? =
    methodAndStartDate.methodChosen?.let {
        ChosenMethod(
            methodChosen = it,
            startDate = methodAndStartDate.startDate.toFormattedString(),
            nextCycle = methodAndStartDate.startDate.plusDays(TOTAL_CYCLE_DAYS).toFormattedString(),
            pillsBreakDays = pillsBreakDays,
            notifications = notifications,
            notificationTime = notificationTime,
            alarm = alarm,
            alarmTime = alarmTime
        )
    }