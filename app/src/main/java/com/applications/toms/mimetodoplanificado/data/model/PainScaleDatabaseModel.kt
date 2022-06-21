package com.applications.toms.mimetodoplanificado.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pain_scale_table")
data class PainScaleDatabaseModel(
    @PrimaryKey
    val date: String,
    val painScale: Int
)
