package com.applications.toms.mimetodoplanificado.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.applications.toms.domain.enums.Method
import java.time.LocalDate

@Entity(tableName = "method_table")
data class ChosenMethod(
    @PrimaryKey val methodChosen: Method,
    val startDate: String,
    val nextCycle: String,
    val pillsBreakDays: Int,
    val notifications: Boolean,
    val notificationTime: String,
    val alarm: Boolean,
    val alarmTime: String
)
