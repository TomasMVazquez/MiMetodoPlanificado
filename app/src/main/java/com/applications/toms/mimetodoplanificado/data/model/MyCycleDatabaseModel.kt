package com.applications.toms.mimetodoplanificado.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycle_table")
data class MyCycleDatabaseModel(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val startDate: String,
    val nextCycle: String,
    val totalDaysCycle: Long = 28L
)
