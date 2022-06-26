package com.applications.toms.mimetodoplanificado.data.mapper

import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.MyCycle
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.mimetodoplanificado.data.model.ChosenMethod
import com.applications.toms.mimetodoplanificado.data.model.MyCycleDatabaseModel
import com.applications.toms.mimetodoplanificado.data.model.PainScaleDatabaseModel
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
            notifications = isNotificationEnable,
            notificationTime = notificationTime,
            alarm = isAlarmEnable,
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
        isNotificationEnable = this.notifications,
        notificationTime = this.notificationTime,
        isAlarmEnable = this.alarm,
        alarmTime = this.alarmTime
    )

fun MyCycleDatabaseModel.toModel(formatter: DateTimeFormatter): MyCycle =
    MyCycle(
        id = this.id,
        startDate = LocalDate.parse(this.startDate, formatter),
        nextCycle = LocalDate.parse(this.nextCycle, formatter),
        totalDaysCycle = this.totalDaysCycle
    )

fun MyCycle.toDatabaseModel(): MyCycleDatabaseModel =
    MyCycleDatabaseModel(
        id = 0,
        startDate = this.startDate.toFormattedString(),
        nextCycle = this.nextCycle.toFormattedString(),
        totalDaysCycle = this.totalDaysCycle
    )

fun PainScaleModel.toDatabaseModel(): PainScaleDatabaseModel =
    PainScaleDatabaseModel(
        date = this.date.toFormattedString(),
        painScale = this.painScale,
        dayOfCycle = this.dayOfCycle
    )

fun PainScaleDatabaseModel.toModel(formatter: DateTimeFormatter): PainScaleModel =
    PainScaleModel(
        date = LocalDate.parse(this.date, formatter),
        painScale = this.painScale,
        dayOfCycle = dayOfCycle
    )