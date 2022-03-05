package com.applications.toms.mimetodoplanificado.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val FORMATTED_DATE_PATTERN = "dd/MM/yy"

fun LocalDate.toFormattedString(): String = format(DateTimeFormatter.ofPattern(FORMATTED_DATE_PATTERN))