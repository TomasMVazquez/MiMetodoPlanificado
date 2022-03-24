package com.applications.toms.mimetodoplanificado.data.mapper

import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MethodChosen.toDatabaseModel(): ChosenMethod? =
    methodAndStartDate.methodChosen?.let {
        ChosenMethod(
            methodChosen = it,
            startDate = methodAndStartDate.startDate.toFormattedString(),
            nextCycle = methodAndStartDate.startDate.plusDays(totalDaysCycle).toFormattedString(),
            totalDaysCycle = totalDaysCycle,
            breakDays = breakDays,
            notifications = notifications,
            notificationTime = notificationTime,
            alarm = alarm,
            alarmTime = alarmTime
        )
    }

fun ChosenMethod.toModel(formatter: DateTimeFormatter): MethodChosen =
    MethodChosen(
        methodAndStartDate = MethodAndStartDate(
            methodChosen = this.methodChosen,
            startDate = LocalDate.parse(this.startDate, formatter)
        ),
        totalDaysCycle = this.totalDaysCycle,
        breakDays = this.breakDays,
        notifications = this.notifications,
        notificationTime = this.notificationTime,
        alarm = this.alarm,
        alarmTime = this.alarmTime
    )